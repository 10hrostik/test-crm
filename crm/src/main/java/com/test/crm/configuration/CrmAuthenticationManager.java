package com.test.crm.configuration;

import com.test.crm.exceptions.NotAuthenticatedException;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class CrmAuthenticationManager implements AuthenticationManager {
  @Autowired
  private JwtProvider jwtProvider;

  @Override
  public Authentication authenticate(Authentication authentication) {
    String authToken = authentication.getCredentials().toString();
    String username;
    try {
      username = jwtProvider.extractUsername(authToken);
    } catch (RuntimeException e) {
      throw new NotAuthenticatedException(e.getMessage());
    }
    if (username != null && jwtProvider.validateToken(authToken)) {
      Claims claims = jwtProvider.getClaimsFromToken(authToken);
      List<List<Map<String, String>>> role = claims.get("role", List.class);
      List<SimpleGrantedAuthority> authorities = new ArrayList<>();
      for (List<Map<String, String>> str : role) {
        authorities.add(new SimpleGrantedAuthority(str.getFirst().get("authority")));
      }
      return new UsernamePasswordAuthenticationToken(
          username,
          null,
          authorities
      );
    } else {
      return null;
    }
  }
}
