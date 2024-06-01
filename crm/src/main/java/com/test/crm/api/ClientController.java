package com.test.crm.api;

import com.test.crm.services.ClientService;
import com.test.crm.services.models.client.ResponseClientDto;
import com.test.crm.services.models.client.UpdateClientRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/secured/v1/clients")
public class ClientController {

  private final ClientService service;

  @PostMapping("/search")
  public ResponseEntity<List<ResponseClientDto>> search(@RequestBody Map<String, Object> searchRequest) {
    return ResponseEntity.ok(service.search(searchRequest));
  }

  @PatchMapping("/deactivate/{id}")
  public ResponseEntity<?> deactivate(@PathVariable String id) {
    service.deactivate(id);
    return ResponseEntity.noContent().build();
  }

  @PutMapping("/{id}")
  public ResponseEntity<ResponseClientDto> update(@RequestBody UpdateClientRequest request, @PathVariable String id) {
    return ResponseEntity.ok(service.update(request, id));
  }
}
