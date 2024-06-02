package com.test.crm.configuration;

import com.test.crm.models.user.User;
import com.test.crm.services.models.client.ResponseUserDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class JwtProvider {
  @Value("${jwt.secret}")
  private String secret;

  @Value("${jwt.expiration}")
  private String expirationTime;

  public String extractUsername(String authToken) {
    return getClaimsFromToken(authToken)
        .getSubject();
  }

  public Claims getClaimsFromToken(String authToken) {
    String key = Base64.getEncoder().encodeToString(secret.getBytes());
    return Jwts.parserBuilder()
        .setSigningKey(key)
        .build()
        .parseClaimsJws(authToken)
        .getBody();
  }

  public boolean validateToken(String authToken) {
    return getClaimsFromToken(authToken)
        .getExpiration()
        .after(new Date());
  }

  public String generateToken(ResponseUserDto client) {
    Map<String, Object> claims = new HashMap<>();
    claims.put("role", List.of(client.getRole()));

    return generateToken(claims, client.getUsername());
  }

  public String generateToken(User user) {
    Map<String, Object> claims = new HashMap<>();
    claims.put("role", List.of(user.getAuthorities()));

    return generateToken(claims, user.getUsername());
  }

  private String generateToken(Map<String, Object> claims, String credential) {
    long expirationSeconds = Long.parseLong(expirationTime);
    Date creationDate = new Date();
    Date expirationDate = new Date(creationDate.getTime() + expirationSeconds * 1440);

    return Jwts.builder()
        .setClaims(claims)
        .setSubject(credential)
        .setIssuedAt(creationDate)
        .setExpiration(expirationDate)
        .signWith(Keys.hmacShaKeyFor(secret.getBytes()))
        .compact();
  }
}
