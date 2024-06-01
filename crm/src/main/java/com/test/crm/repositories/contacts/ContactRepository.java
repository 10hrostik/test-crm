package com.test.crm.repositories.contacts;

import com.test.crm.models.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactRepository extends JpaRepository<Contact, String> {

  List<Contact> findByClientId(String clientId);
}
