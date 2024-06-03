package com.test.crm;

import com.test.crm.models.Contact;
import com.test.crm.models.task.Task;
import com.test.crm.models.task.TaskStatus;
import com.test.crm.repositories.TaskRepository;
import com.test.crm.services.ContactService;
import com.test.crm.services.TaskService;
import com.test.crm.services.models.UpdateTaskStatusRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class TaskServiceTest {

  @Mock
  private ContactService contactService;

  @Mock
  private TaskRepository repository;

  @Mock
  private SimpMessagingTemplate messagingTemplate;

  @InjectMocks
  private TaskService taskService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testGetAssingedTasks() {
    Task task1 = new Task();
    Task task2 = new Task();
    List<Task> tasks = Arrays.asList(task1, task2);

    when(repository.findAllByAssigneeId("1")).thenReturn(tasks);

    List<Task> result = taskService.getAssingedTasks("1");

    assertNotNull(result);
    assertEquals(2, result.size());
    verify(repository, times(1)).findAllByAssigneeId("1");
  }

  @Test
  @Transactional
  void testUpdateStatus() {
    Task task = new Task();
    task.setId("1");
    task.setStatus(TaskStatus.OPEN);

    UpdateTaskStatusRequest request = new UpdateTaskStatusRequest();
    request.setId("1");
    request.setStatus(TaskStatus.IN_PROGRESS);
    when(repository.findById("1")).thenReturn(Optional.of(task));

    Task result = taskService.updateStatus(request);

    assertNotNull(result);
    assertEquals(TaskStatus.IN_PROGRESS, result.getStatus());
    verify(repository, times(1)).findById("1");
  }

  @Test
  @Transactional
  void testAssignTask() {
    Task task = new Task();
    task.setId("1");

    Contact contact = new Contact();
    contact.setId("1");

    when(repository.findById("1")).thenReturn(Optional.of(task));
    when(contactService.getExistent("1")).thenReturn(contact);

    Task result = taskService.assignTask("1", "1");

    assertNotNull(result);
    verify(repository, times(1)).findById("1");
    verify(contactService, times(1)).getExistent("1");
  }

  @Test
  @Transactional
  void testUpdate() {
    Task task = new Task();
    task.setId("1");

    when(repository.save(task)).thenReturn(task);

    Task result = taskService.update(task);

    assertNotNull(result);
    assertEquals("1", result.getId());
    verify(repository, times(1)).save(task);
  }
}
