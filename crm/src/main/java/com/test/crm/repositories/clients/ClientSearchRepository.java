package com.test.crm.repositories.clients;

import com.test.crm.models.Client;
import com.test.crm.repositories.BaseSearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public class ClientSearchRepository extends BaseSearchRepository<Client> {

  public ClientSearchRepository() {
    super(Client.class);
  }
}
