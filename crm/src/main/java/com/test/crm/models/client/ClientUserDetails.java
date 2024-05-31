package com.test.crm.models.client;

import org.springframework.security.core.userdetails.UserDetails;

public interface ClientUserDetails extends UserDetails {
  @Override
  default boolean isAccountNonExpired() {
    return true;
  }

  @Override
  default boolean isAccountNonLocked() {
    return true;
  }

  @Override
  default boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  default boolean isEnabled() {
    return true;
  }
}
