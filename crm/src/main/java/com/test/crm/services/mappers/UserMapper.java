package com.test.crm.services.mappers;

import com.test.crm.models.user.User;
import com.test.crm.services.models.client.ResponseUserDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

  User asUser(String username, String password);

  ResponseUserDto asResponse(User user);
}
