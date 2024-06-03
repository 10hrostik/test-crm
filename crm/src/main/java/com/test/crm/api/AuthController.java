package com.test.crm.api;

import com.test.crm.api.validators.RegisterClientValidator;
import com.test.crm.services.UserService;
import com.test.crm.services.models.client.CreateUserRequest;
import com.test.crm.services.models.client.ResponseUserDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/public/auth")
public class AuthController {

  private final UserService service;
  private final RegisterClientValidator registerClientValidator;

  @InitBinder("сreateClientRequest")
  protected void initBinder(WebDataBinder binder) {
    binder.addValidators(registerClientValidator);
  }

  @PostMapping(value = "/login")
  public ResponseEntity<ResponseUserDto> login(@RequestParam String username,
                                               @RequestParam String password) {
    return ResponseEntity.ok(service.login(username, password));
  }

  @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ResponseUserDto> register(@RequestBody @Valid CreateUserRequest request) {
    return ResponseEntity.ok(service.register(request));
  }

  @GetMapping(value = "/check-login")
  public ResponseEntity<ResponseUserDto> checkLogin(Authentication authentication) {
    return ResponseEntity.ok(service.checkLogin(authentication));
  }
}
