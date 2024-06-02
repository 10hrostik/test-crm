package com.test.crm.models.user;

import org.springframework.security.core.GrantedAuthority;

public enum UserRole implements GrantedAuthority {
  USER, ADMIN;

  @Override
  public String getAuthority() {
    return this.name();
  }
}
