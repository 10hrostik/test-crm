package com.test.crm.services;

import com.test.crm.models.Contact;
import com.test.crm.models.Contact_;
import com.test.crm.repositories.contacts.ContactRepository;
import com.test.crm.repositories.contacts.ContactSearchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class ContactService extends BaseService<Contact> {

  private final ContactRepository repository;
  private final ContactSearchRepository searchRepository;

  public List<Contact> getListByClient(String clientId) {
    return repository.findByClientId(clientId);
  }

  public List<Contact> search(Map<String, Object> fields) {
    if (fields.containsKey(Contact_.ID)) {
      return List.of(getExistent(fields.get(Contact_.ID).toString()));
    }
    return searchRepository.searchByFields(fields);
  }

  @Override
  protected JpaRepository<Contact, String> getRepository() {
    return repository;
  }
}
