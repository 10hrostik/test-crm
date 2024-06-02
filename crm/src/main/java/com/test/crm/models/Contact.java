package com.test.crm.models;

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

  @Column(name = "user_id")
  private String user;
}
