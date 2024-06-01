package com.test.crm.api;

import com.test.crm.api.validators.RegisterClientValidator;
import com.test.crm.services.ClientService;
import com.test.crm.services.models.client.CreateClientRequest;
import com.test.crm.services.models.client.ResponseClientDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/public/auth")
public class AuthController {

  private final ClientService service;
  private final RegisterClientValidator registerClientValidator;

  @InitBinder("registerClientRequest")
  protected void initBinder(WebDataBinder binder) {
    binder.addValidators(registerClientValidator);
  }

  @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ResponseClientDto> login(@RequestParam String username,
                                                 @RequestParam String password,
                                                 HttpServletRequest request) throws ServletException {
    request.login(username, password);
    return ResponseEntity.ok(service.login(username, password));
  }

  @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ResponseClientDto> register(@RequestBody CreateClientRequest request) {
    return ResponseEntity.ok(service.register(request));
  }
}
