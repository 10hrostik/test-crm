package com.test.crm.models;

import com.test.crm.models.client.Client;
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
  @Column(name = "target_client_id")
  private String targetClientId;

  @ManyToOne
  @JoinColumn(name = "client_id", referencedColumnName = "id")
  private Client client;
}
