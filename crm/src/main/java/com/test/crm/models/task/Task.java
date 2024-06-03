package com.test.crm.models.task;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.test.crm.models.BaseEntity;
import com.test.crm.models.Contact;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Table(name = "tasks")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Task extends BaseEntity implements Serializable {
  @Basic
  @Size(max = 2000)
  private String description;

  @Column(name = "dead_line")
  private LocalDateTime deadLine;

  @Column(name = "dead_line_notified")
  private boolean deadLineNotified = false;

  @NotNull
  @Enumerated(EnumType.STRING)
  @Column(name = "status")
  private TaskStatus status;

  @NotEmpty
  @Column(name = "created_by")
  private String createdBy;

  @ManyToOne
  @JoinColumn(name = "assignee_id", referencedColumnName = "id")
  private Contact assignee;
}
