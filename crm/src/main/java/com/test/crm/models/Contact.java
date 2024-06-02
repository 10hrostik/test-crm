package com.test.crm.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.test.crm.models.task.Task;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "contacts")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Contact extends BaseEntity {
  @Basic
  private String name;

  @Basic
  private String surname;

  @Basic
  private String phone;

  @Basic
  private String email;

  @OneToMany(mappedBy = "assignee")
  private List<Task> tasks;

  @NotEmpty
  @ManyToOne
  @JoinColumn(name = "client_id")
  private Client client;

  @Column(name = "created_by")
  private String createdBy;
}
