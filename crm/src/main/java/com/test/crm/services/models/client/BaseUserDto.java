package com.test.crm.services.models.client;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class BaseUserDto {
  @NotEmpty
  @Size(min = 5)
  private String username;
}
