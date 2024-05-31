package com.test.crm.models;

import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

@Setter
@Getter
@MappedSuperclass
public class BaseEntity {
  @Id
  @UuidGenerator(style = UuidGenerator.Style.TIME)
  private String id;
}
