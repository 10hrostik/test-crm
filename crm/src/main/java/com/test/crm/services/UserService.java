package com.test.crm.services;

import com.test.crm.configuration.JwtProvider;
import com.test.crm.exceptions.NotAuthenticatedException;
import com.test.crm.exceptions.UserExistsException;
import com.test.crm.models.user.User;
import com.test.crm.repositories.UserRepository;
import com.test.crm.services.mappers.UserMapper;
import com.test.crm.services.models.client.CreateUserRequest;
import com.test.crm.services.models.client.ResponseUserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.test.crm.exceptions.ErrorMessages.*;

@Service
@Primary
public class UserService extends BaseService<User> implements UserDetailsService {

  private UserRepository repository;
  private PasswordEncoder passwordEncoder;
  private UserMapper mapper;
  private JwtProvider jwtProvider;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return Optional.ofNullable(repository.findByUsernameAndEnabled(username, true))
        .orElseThrow(() -> new UsernameNotFoundException(INVALID_CREDENTIALS));
  }

  public ResponseUserDto login(String username, String password) {
    User user = Optional.ofNullable(repository.findByUsernameAndPasswordAndEnabled(username, passwordEncoder.encode(password), true))
        .orElseThrow(() -> new UsernameNotFoundException(INVALID_CREDENTIALS));
    ResponseUserDto convertedUser = mapper.asResponse(user);
    return populateWithToken(convertedUser);
  }

  @Transactional
  public ResponseUserDto register(CreateUserRequest request) {
    String password = passwordEncoder.encode(request.getPassword());
    if (repository.findByUsername(request.getUsername()) != null) {
      throw new UserExistsException(USERNAME_ALREADY_EXISTS);
    }
    User savedUser = save(mapper.asUser(request.getUsername(), password));
    ResponseUserDto convertedUser = mapper.asResponse(savedUser);
    return populateWithToken(convertedUser);
  }

  @Transactional
  public void deactivate(String id) {
    User user = getExistent(id);
    user.setEnabled(false);
  }

  public ResponseUserDto checkLogin(Authentication authentication) {
    if (authentication.isAuthenticated()) {
      User user = (User) loadUserByUsername(authentication.getPrincipal().toString());
      ResponseUserDto convertedClient = mapper.asResponse(user);
      return populateWithToken(convertedClient);
    }
    throw new NotAuthenticatedException(NOT_AUTH);
  }

  private ResponseUserDto populateWithToken(ResponseUserDto user) {
      user.setToken(jwtProvider.generateToken(user));
      return user;
  }

  @Autowired
  public void setJwtProvider(JwtProvider jwtProvider) {
    this.jwtProvider = jwtProvider;
  }

  @Autowired
  public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
    this.passwordEncoder = passwordEncoder;
  }

  @Autowired
  public void setRepository(UserRepository repository) {
    this.repository = repository;
  }

  @Autowired
  public void setMapper(UserMapper mapper) {
    this.mapper = mapper;
  }

  @Override
  protected JpaRepository<User, String> getRepository() {
    return repository;
  }
}
