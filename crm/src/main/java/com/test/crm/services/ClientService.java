package com.test.crm.services;

import com.test.crm.models.Client;
import com.test.crm.repositories.clients.ClientRepository;
import com.test.crm.repositories.clients.ClientSearchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class ClientService extends BaseService<Client> {

  private final ClientRepository repository;
  private final ClientSearchRepository searchRepository;

  public List<Client> search(Map<String, Object> fields) {
    return searchRepository.searchByFields(fields);
  }

  @Override
  protected JpaRepository<Client, String> getRepository() {
    return repository;
  }
}
