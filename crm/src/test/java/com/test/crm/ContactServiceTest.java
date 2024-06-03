package com.test.crm;

import com.test.crm.models.Contact;
import com.test.crm.models.Contact_;
import com.test.crm.repositories.contacts.ContactRepository;
import com.test.crm.repositories.contacts.ContactSearchRepository;
import com.test.crm.services.ContactService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class ContactServiceTest {

  @Mock
  private ContactRepository repository;

  @Mock
  private ContactSearchRepository searchRepository;

  @InjectMocks
  private ContactService contactService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testSearchById() {
    Contact contact = new Contact();
    contact.setId("1");

    when(repository.findById("1")).thenReturn(Optional.of(contact));

    Map<String, Object> fields = Map.of(Contact_.ID, "1");
    List<Contact> result = contactService.search(fields);

    assertNotNull(result);
    assertEquals(1, result.size());
    assertEquals("1", result.getFirst().getId());
    verify(repository, times(1)).findById("1");
    verify(searchRepository, never()).searchByFields(anyMap());
  }

  @Test
  void testSearchByFields() {
    Contact contact1 = new Contact();
    Contact contact2 = new Contact();
    contact1.setName("John Doe");
    contact2.setName("John Doe");
    List<Contact> contacts = List.of(contact1, contact2);

    when(searchRepository.searchByFields(anyMap())).thenReturn(contacts);

    Map<String, Object> fields = Map.of(Contact_.NAME, "John Doe");
    List<Contact> result = contactService.search(fields);

    assertNotNull(result);
    assertEquals(2, result.size());
    verify(searchRepository, times(1)).searchByFields(anyMap());
    verify(repository, never()).findById(anyString());
  }

  @Test
  void testSearchByIdNotFound() {
    when(repository.findById("1")).thenReturn(Optional.empty());

    Map<String, Object> fields = Map.of(Contact_.ID, "1");
    RuntimeException exception = assertThrows(RuntimeException.class, () -> contactService.search(fields));

    assertEquals("No record found with id: 1", exception.getMessage());
    verify(repository, times(1)).findById("1");
  }
}