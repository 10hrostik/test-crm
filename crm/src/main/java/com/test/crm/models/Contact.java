package com.test.crm.models;

import jakarta.persistence.Basic;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "contacts")
public class Contact extends BaseEntity {
  @Basic
  private String name;

  @Basic
  private String surname;

  @Basic
  private String phone;

  @Basic
  private String email;
}
