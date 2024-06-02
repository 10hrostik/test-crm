package com.test.crm.configuration;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.context.HttpRequestResponseHolder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Component;

@Component
public class SimpleSecurityContextRepository implements SecurityContextRepository {
  @Autowired
  private AuthenticationManager authenticationManager;

  @Override
  public SecurityContext loadContext(HttpRequestResponseHolder requestResponseHolder) {
    String authHeader = requestResponseHolder.getRequest().getHeader(HttpHeaders.AUTHORIZATION);
    if (authHeader != null && authHeader.startsWith("Bearer ")) {
      String authToken = authHeader.substring(7);
      UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(authToken, authToken);
      Authentication authentication = authenticationManager.authenticate(auth);
      return new SecurityContextImpl(authentication);
    }
    return null;
  }

  @Override
  public void saveContext(SecurityContext context, HttpServletRequest request, HttpServletResponse response) {

  }

  @Override
  public boolean containsContext(HttpServletRequest request) {
    return false;
  }
}
