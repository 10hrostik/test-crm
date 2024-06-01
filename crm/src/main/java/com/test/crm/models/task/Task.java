package com.test.crm.models.task;

import com.test.crm.models.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Table(name = "tasks")
public class Task extends BaseEntity {
  @Basic
  @Size(max = 2000)
  private String description;

  @Basic
  private LocalDateTime deadLine;

  @NotNull
  @Enumerated(EnumType.STRING)
  @Column(name = "status")
  private TaskStatus status;

  @NotEmpty
  @Column(name = "created_by")
  private String createdBy;

  @Column(name = "assignee_id")
  private String assingeeId;
}
