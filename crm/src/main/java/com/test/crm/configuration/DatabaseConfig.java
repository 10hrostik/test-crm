package com.test.crm.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.*;

@Slf4j
@Component
public class DatabaseConfig {
  @Value("${postgres.connection}")
  private String connectionString;

  @Value("${postgres.super.user}")
  private String superUser;

  @Value("${postgres.super.password}")
  private String superPassword;

  @Value("${spring.application.name}")
  private String applicationName;

  public void init() {
    createDatabase();
    createAuthorities();
  }

  private void createDatabase() {
    try (Connection connection = DriverManager.getConnection(connectionString, superUser, superPassword)) {
      if (checkIfDatabaseNotExists(connection)) {
        String query = """
         CREATE DATABASE schoolify;
         REVOKE ALL ON SCHEMA public FROM PUBLIC;
         REVOKE ALL ON DATABASE schoolify FROM PUBLIC;
         """;

        Statement statement = connection.createStatement();
        statement.execute(query);
      }
    } catch (SQLException e) {
      log.error(e.toString());
    }
  }

  private void createAuthorities() {
    try (Connection connection = DriverManager.getConnection(connectionString + applicationName, superUser, superPassword)) {
      if (checkIfAuthoritiesNotExists(connection)) {
        String query = """
         CREATE ROLE crm_super_role;
         GRANT CONNECT ON DATABASE crm TO crm_super_role;
         GRANT ALL ON DATABASE crm TO transparent_super_role;
         CREATE USER crm_flyway_user NOSUPERUSER NOCREATEDB NOCREATEROLE LOGIN ENCRYPTED PASSWORD 'VwJkf4nHIpbl';
         GRANT ALL ON DATABASE crm TO crm_flyway_user;
         GRANT ALL PRIVILEGES ON DATABASE crm TO crm_flyway_user;
         CREATE ROLE crm_service_role;
         GRANT CONNECT ON DATABASE crm TO crm_service_role;
         CREATE USER crm_service NOSUPERUSER NOCREATEDB NOCREATEROLE LOGIN ENCRYPTED PASSWORD 'XfExc8ZTbsze';
         GRANT crm_service_role TO crm_service;
         """;

        Statement statement = connection.createStatement();
        statement.execute(query);
      }
    } catch (SQLException e) {
      log.error(e.toString());
    }
  }

  private boolean checkIfDatabaseNotExists(Connection connection) throws SQLException {
    String query = "SELECT * FROM pg_database WHERE datname = 'crm'";
    Statement statement = connection.createStatement();
    statement.execute(query);
    ResultSet resultSet = statement.getResultSet();

    return !resultSet.next();
  }

  private boolean checkIfAuthoritiesNotExists(Connection connection) throws SQLException {
    String query = "SELECT * FROM pg_user AS users WHERE usename = 'crm' OR usename = 'crm_flyway_user'";
    Statement statement = connection.createStatement();
    statement.execute(query);
    ResultSet resultSet = statement.getResultSet();

    return !resultSet.next();
  }
}
