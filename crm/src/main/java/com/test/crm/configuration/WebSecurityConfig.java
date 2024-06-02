package com.test.crm.configuration;

import com.test.crm.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
  @Autowired
  private CrmAuthenticationManager authenticationManager;
  @Autowired
  private SimpleSecurityContextRepository repository;

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.csrf(AbstractHttpConfigurer::disable)
        .cors(corsConfigurer -> corsConfigurer.configurationSource(corsConfigurationSource()))
        .formLogin(AbstractHttpConfigurer::disable)
        .authenticationManager(authenticationManager)
        .securityContext(configurer -> configurer.securityContextRepository(repository))
        .authorizeHttpRequests(registry -> registry.requestMatchers("/secured/**").authenticated()
            .requestMatchers("/public/**").permitAll())
        .logout(AbstractHttpConfigurer::disable)
        .headers(Customizer.withDefaults())
        .httpBasic(Customizer.withDefaults());

    return http.build();
  }


  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedOrigins(List.of("http://localhost:3000"));
    configuration.setAllowedMethods(Arrays.asList(HttpMethod.POST.name(), HttpMethod.GET.name(),
        HttpMethod.PATCH.name(), HttpMethod.PUT.name(), HttpMethod.DELETE.name(), HttpMethod.OPTIONS.name()));
    configuration.setAllowedHeaders(Arrays.asList("Accept", "Content-Type", "Authorization"));
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }

  @Bean
  public ClientService authenticationService() {
    return new ClientService();
  }
}
