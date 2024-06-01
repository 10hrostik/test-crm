package com.test.crm.services.models;

import com.test.crm.models.task.TaskStatus;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateTaskStatusRequest {
  @NotEmpty
  private String id;
  @NotEmpty
  private TaskStatus status;
}
