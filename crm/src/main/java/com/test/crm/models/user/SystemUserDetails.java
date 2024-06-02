package com.test.crm.models.user;

import org.springframework.security.core.userdetails.UserDetails;

public interface SystemUserDetails extends UserDetails {
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
}
