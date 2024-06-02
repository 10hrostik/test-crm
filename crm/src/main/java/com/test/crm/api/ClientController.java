package com.test.crm.api;

import com.test.crm.models.Client;
import com.test.crm.services.ClientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/secured/v1/clients")
public class ClientController {

  private final ClientService service;

  @GetMapping("/all")
  public ResponseEntity<List<Client>> getAll() {
    return ResponseEntity.ok(service.getAll());
  }

  @PostMapping
  public ResponseEntity<Client> create(@RequestBody @Valid Client client) {
    return ResponseEntity.ok(service.save(client));
  }

  @PostMapping("/search")
  public ResponseEntity<List<Client>> search(@RequestBody Map<String, Object> searchRequest) {
    return ResponseEntity.ok(service.search(searchRequest));
  }

  @PutMapping()
  public ResponseEntity<Client> update(@RequestBody Client client) {
    return ResponseEntity.ok(service.update(client));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable String id) {
    service.delete(id);
    return ResponseEntity.noContent().build();
  }
}
