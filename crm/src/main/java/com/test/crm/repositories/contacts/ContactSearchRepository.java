package com.test.crm.repositories.contacts;

import com.test.crm.models.Contact;
import com.test.crm.repositories.BaseSearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public class ContactSearchRepository extends BaseSearchRepository<Contact> {

  public ContactSearchRepository() {
    super(Contact.class);
  }
}
