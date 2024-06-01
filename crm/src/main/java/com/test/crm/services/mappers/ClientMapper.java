package com.test.crm.services.mappers;

import com.test.crm.models.client.Client;
import com.test.crm.services.models.client.ResponseClientDto;
import com.test.crm.services.models.client.UpdateClientRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ClientMapper {

  Client asClient(String username, String password);

  @Mapping(target = "username", ignore = true)
  @Mapping(target = "password", ignore = true)
  Client asClient(@MappingTarget Client client, UpdateClientRequest request);

  ResponseClientDto asResponse(Client client);
}
