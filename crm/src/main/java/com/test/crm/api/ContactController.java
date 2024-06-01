package com.test.crm.api;

import com.test.crm.models.Contact;
import com.test.crm.services.ContactService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/secured/v1/contacts")
public class ContactController {

  private final ContactService service;

  @PostMapping
  public ResponseEntity<Contact> create(@RequestBody @Valid Contact contact) {
    return ResponseEntity.ok(service.save(contact));
  }

  @PostMapping("/search")
  public ResponseEntity<List<Contact>> search(@RequestBody Map<String, Object> searchRequest) {
    return ResponseEntity.ok(service.search(searchRequest));
  }

  @GetMapping
  public ResponseEntity<List<Contact>> getClientContacts(@RequestParam String clientId) {
    return ResponseEntity.ok(service.getListByClient(clientId));
  }

  @PutMapping
  public ResponseEntity<Contact> update(@RequestBody @Valid Contact contact) {
    return ResponseEntity.ok(service.update(contact));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> delete(@PathVariable String id) {
    service.delete(id);
    return ResponseEntity.noContent().build();
  }
}
