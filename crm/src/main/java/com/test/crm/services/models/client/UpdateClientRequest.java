package com.test.crm.services.models.client;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateClientRequest extends CreateClientRequest {
  private String companyName;
  private String branch;
}
