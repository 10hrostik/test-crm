package com.test.crm.services.models.client;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.test.crm.models.client.ClientRole;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseClientDto extends BaseClientDto {
  private String id;
  private String companyName;
  private String branch;
  private String token;
  @JsonIgnore
  private ClientRole role;
}
