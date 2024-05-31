package com.test.crm.models.client;

import org.springframework.security.core.GrantedAuthority;

public enum ClientRole implements GrantedAuthority {
  USER, ADMIN;


  @Override
  public String getAuthority() {
    return this.name();
  }
}
