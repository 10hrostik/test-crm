package com.test.crm.services;

import com.test.crm.models.task.Task;
import com.test.crm.repositories.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class TaskScheduleService {

  private final TaskRepository repository;
  private final SimpMessagingTemplate messagingTemplate;
  private static final int BATCH_SIZE = 100;

  @Scheduled(cron = "${task.notify.deadline.cron}")
  public void notifyDeadLine() {
    PageRequest pageable = PageRequest.of(0, BATCH_SIZE);
    int responseSize;
    do {
      List<Task> tasks = repository.findAllByDeadLineBeforeAndDeadLineNotifiedIsFalse(LocalDateTime.now().minusHours(8), pageable);
      tasks.forEach(task -> {
        messagingTemplate.convertAndSend("/task/notify", task);
        task.setDeadLineNotified(true);
      });
      repository.saveAll(tasks);
      responseSize = tasks.size();
    } while (BATCH_SIZE == responseSize);
  }
}
