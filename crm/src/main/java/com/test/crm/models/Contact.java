package com.test.crm.models;

import com.test.crm.models.client.Client;
import jakarta.persistence.*;
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

  @ManyToOne
  @JoinColumn(name = "client_id", referencedColumnName = "id")
  private Client client;
}
