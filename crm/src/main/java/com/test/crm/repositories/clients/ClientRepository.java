package com.test.crm.repositories.clients;

import com.test.crm.models.client.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, String> {
  Client findByUsername(String username);

  Client findByUsernameAndEnabled(String username, boolean enabled);

  Client findByUsernameAndPassword(String username, String password);
}
