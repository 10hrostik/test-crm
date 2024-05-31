package com.test.crm.models.task;

import com.test.crm.models.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Table(name = "tasks")
public class Task extends BaseEntity {
  @Basic
  private String description;

  @Basic
  private LocalDateTime deadLine;

  @Enumerated(EnumType.STRING)
  @Column(name = "status", nullable = false)
  private TaskStatus status;

  @Column(name = "created_by")
  private String createdBy;
}
