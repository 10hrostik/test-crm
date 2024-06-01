package com.test.crm.services;

import com.test.crm.models.task.Task;
import com.test.crm.repositories.TaskRepository;
import com.test.crm.services.models.UpdateTaskStatusRequest;
import com.test.crm.utils.TransactionUtils;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TaskService extends BaseService<Task> {

  private final TaskRepository repository;
  private final SimpMessagingTemplate messagingTemplate;

  @Transactional
  public Task create(Task task, String assingeeId) {
    if (StringUtils.isBlank(assingeeId)) {
      return save(task);
    }
    task.setAssingeeId(assingeeId);
    return save(task);
  }

  public List<Task> getAssingedTasks(String assingeeId) {
    return repository.findAllByAssingeeId(assingeeId);
  }

  @Transactional
  public Task updateStatus(UpdateTaskStatusRequest request) {
    Task existent = getExistent(request.getId());
    existent.setStatus(request.getStatus());
    TransactionUtils.afterCommit(() -> messagingTemplate.convertAndSend("/task", existent));
    return existent;
  }

  @Transactional
  public Task update(Task task) {
    Task updatedTask = super.update(task);
    TransactionUtils.afterCommit(() -> messagingTemplate.convertAndSend("/task", updatedTask));
    return updatedTask;
  }

  @Override
  protected JpaRepository<Task, String> getRepository() {
    return repository;
  }
}
