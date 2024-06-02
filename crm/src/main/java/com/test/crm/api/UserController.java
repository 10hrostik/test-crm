package com.test.crm.api;

import com.test.crm.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/secured/v1/users")
public class UserController {

  private final UserService service;

  @PatchMapping(value = "/deactivate/{id}")
  public ResponseEntity<?> deactivate(@PathVariable String id) {
    service.deactivate(id);
    return ResponseEntity.noContent().build();
  }
}
