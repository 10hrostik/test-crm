package com.test.crm.api;

import com.test.crm.models.Contact;
import com.test.crm.services.ContactService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/secured/v1/contacts")
public class ContactController {

  private final ContactService service;

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Contact> create(@RequestBody @Valid Contact contact) {
    return ResponseEntity.ok(service.save(contact));
  }

  @PostMapping(value = "/search")
  public ResponseEntity<List<Contact>> search(@RequestBody Map<String, Object> searchRequest) {
    return ResponseEntity.ok(service.search(searchRequest));
  }

  @GetMapping(value = "/all")
  public ResponseEntity<List<Contact>> getClientContacts() {
    return ResponseEntity.ok(service.getAll());
  }

  @PutMapping
  public ResponseEntity<Contact> update(@RequestBody @Valid Contact contact) {
    return ResponseEntity.ok(service.update(contact));
  }

  @DeleteMapping(value = "/{id}")
  public ResponseEntity<?> delete(@PathVariable String id) {
    service.delete(id);
    return ResponseEntity.noContent().build();
  }
}
