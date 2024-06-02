package com.test.crm.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "clients")
public class Client extends BaseEntity implements Serializable {
  @Column(name = "company_name")
  private String companyName;

  @Basic
  private String branch;

  @Basic
  private String address;

  @OneToMany(mappedBy = "client", orphanRemoval = true)
  private List<Contact> contact;
}
