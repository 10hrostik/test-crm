package com.test.crm.configuration;

import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableJpaRepositories(basePackages = "com.test.crm.repositories")
@PropertySource(value = { "classpath:application.properties" })
@EnableTransactionManagement
public class EntityManagerConfig {
  @Value("${spring.datasource.username}")
  private String dataSourceUsername;

  @Value("${spring.datasource.password}")
  private String dataSourcePassword;

  @Value("${spring.datasource.url}")
  private String dataSourceURL;

  @Value("${spring.datasource.driver-class-name}")
  private String jdbcDriver;

  @Value("${spring.jpa.show-sql}")
  private String showSqlProperty;

  private final String[] ENTITYMANAGER_PACKAGES_TO_SCAN = { "com.test.crm.models" };

  @Bean
  public DataSource dataSource() {
    return DataSourceBuilder.create().username(dataSourceUsername)
        .password(dataSourcePassword)
        .url(dataSourceURL)
        .driverClassName(jdbcDriver)
        .build();
  }

  @Bean(name = "transactionManager")
  public JpaTransactionManager jpaTransactionManager() {
    JpaTransactionManager transactionManager = new JpaTransactionManager();
    transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
    return transactionManager;
  }

  @Bean
  public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
    LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
    entityManagerFactoryBean.setJpaVendorAdapter(vendorAdaptor());
    entityManagerFactoryBean.setDataSource(dataSource());
    entityManagerFactoryBean.setPersistenceProviderClass(HibernatePersistenceProvider.class);
    entityManagerFactoryBean.setPackagesToScan(ENTITYMANAGER_PACKAGES_TO_SCAN);
    entityManagerFactoryBean.setJpaProperties(addProperties());

    return entityManagerFactoryBean;
  }

  private HibernateJpaVendorAdapter vendorAdaptor() {
    return new HibernateJpaVendorAdapter();
  }

  private Properties addProperties() {
    Properties properties = new Properties();
    properties.setProperty("hibernate.show_sql", showSqlProperty);
    return properties;
  }
}
