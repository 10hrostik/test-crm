package com.test.crm.api.validators;

import com.test.crm.services.models.client.CreateClientRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class RegisterClientValidator implements Validator {

  @Override
  public boolean supports(Class<?> clazz) {
    return CreateClientRequest.class.equals(clazz);
  }

  @Override
  public void validate(Object target, Errors errors) {
    CreateClientRequest createClientRequest = (CreateClientRequest) target;
    if (!StringUtils.equals(createClientRequest.getPassword(), createClientRequest.getConfirmPassword())) {
      errors.rejectValue("password", "Passwords do not match");
    }
  }
}
