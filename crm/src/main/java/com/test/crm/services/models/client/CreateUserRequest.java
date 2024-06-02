package com.test.crm.services.models.client;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateUserRequest extends BaseUserDto {
  @NotEmpty
  @Size(min = 6)
  private String password;
  @NotEmpty
  @Size(min = 6)
  private String confirmPassword;
}
