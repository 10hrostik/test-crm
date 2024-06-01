package com.test.crm.services;

import com.test.crm.configuration.JwtProvider;
import com.test.crm.exceptions.UserExistsException;
import com.test.crm.models.client.Client;
import com.test.crm.models.client.Client_;
import com.test.crm.repositories.clients.ClientRepository;
import com.test.crm.repositories.clients.ClientSearchRepository;
import com.test.crm.services.mappers.ClientMapper;
import com.test.crm.services.models.client.CreateClientRequest;
import com.test.crm.services.models.client.ResponseClientDto;
import com.test.crm.services.models.client.UpdateClientRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.test.crm.exceptions.ErrorMessages.USERNAME_ALREADY_EXISTS;

@Service
@Primary
public class ClientService extends BaseService<Client> implements UserDetailsService {

  private ClientRepository repository;
  private PasswordEncoder passwordEncoder;
  private ClientMapper mapper;
  private ClientSearchRepository searchRepository;
  private JwtProvider jwtProvider;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return Optional.ofNullable(repository.findByUsernameAndEnabled(username, true))
        .orElseThrow(() -> new UsernameNotFoundException(username));
  }

  public ResponseClientDto login(String username, String password) {
    Client client = Optional.ofNullable(repository.findByUsernameAndPassword(username, password))
        .orElseThrow(() -> new UsernameNotFoundException(username));
    ResponseClientDto convertedClient = mapper.asResponse(client);
    return populateWithToken(convertedClient);
  }

  @Transactional
  public ResponseClientDto register(CreateClientRequest request) {
    String password = passwordEncoder.encode(request.getPassword());
    if (repository.findByUsername(request.getUsername()) != null) {
      throw new UserExistsException(USERNAME_ALREADY_EXISTS);
    }
    Client savedClient = save(mapper.asClient(request.getUsername(), password));
    ResponseClientDto convertedClient = mapper.asResponse(savedClient);
    return populateWithToken(convertedClient);
  }

  @Transactional
  public ResponseClientDto update(UpdateClientRequest request, String id) {
    Client existent = getExistent(id);
    update(mapper.asClient(existent, request));
    return mapper.asResponse(existent);
  }

  @Transactional
  public void deactivate(String id) {
    Client client = getExistent(id);
    client.setEnabled(false);
  }

  public List<ResponseClientDto> search(Map<String, Object> fields) {
    if (fields.containsKey(Client_.USERNAME)) {
      return List.of(mapper.asResponse(repository.findByUsername(fields.get(Client_.USERNAME).toString())));
    }
    return searchRepository.searchByFields(fields).stream().map(mapper::asResponse).toList();
  }

  private ResponseClientDto populateWithToken(ResponseClientDto client) {
      client.setToken(jwtProvider.generateToken(client));
      return client;
  }

  @Autowired
  public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
    this.passwordEncoder = passwordEncoder;
  }

  @Autowired
  public void setRepository(ClientRepository repository) {
    this.repository = repository;
  }

  @Autowired
  public void setMapper(ClientMapper mapper) {
    this.mapper = mapper;
  }

  @Autowired
  public void setSearchRepository(ClientSearchRepository searchRepository) {
    this.searchRepository = searchRepository;
  }

  @Override
  protected JpaRepository<Client, String> getRepository() {
    return repository;
  }
}
