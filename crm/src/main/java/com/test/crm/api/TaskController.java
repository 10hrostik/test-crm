package com.test.crm.api;

import com.test.crm.models.task.Task;
import com.test.crm.services.TaskService;
import com.test.crm.services.models.UpdateTaskStatusRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/secured/v1/tasks")
public class TaskController {

  private final TaskService service;

  @PostMapping
  public ResponseEntity<Task> create(@RequestBody @Valid Task task, @RequestParam String clientId) {
    return ResponseEntity.ok(service.create(task, clientId));
  }

  @GetMapping
  public ResponseEntity<List<Task>> getAssignedTasks(@RequestParam String assingeeId) {
    return ResponseEntity.ok(service.getAssingedTasks(assingeeId));
  }

  @PutMapping
  public ResponseEntity<Task> update(@RequestBody @Valid Task task) {
    return ResponseEntity.ok(service.update(task));
  }

  @PatchMapping
  public ResponseEntity<Task> updateStatus(@RequestBody @Valid UpdateTaskStatusRequest request) {
    return ResponseEntity.ok(service.updateStatus(request));
  }

  @PatchMapping("/assign")
  public ResponseEntity<Task> assignTask(@RequestBody @Valid Task task, @RequestParam String contactId) {
    return ResponseEntity.ok(service.assignTask(task, contactId));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable String id) {
    service.delete(id);
    return ResponseEntity.noContent().build();
  }
}
