package com.test.crm.services.models.client;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.test.crm.models.user.UserRole;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseUserDto extends BaseUserDto {
  private String id;
  private String username;
  private String token;
  @JsonIgnore
  private UserRole role;
}
