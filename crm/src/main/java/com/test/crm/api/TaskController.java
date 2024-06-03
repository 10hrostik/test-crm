package com.test.crm.api;

import com.test.crm.models.task.Task;
import com.test.crm.services.TaskService;
import com.test.crm.services.models.UpdateTaskStatusRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/secured/v1/tasks")
public class TaskController {

  private final TaskService service;

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Task> create(@RequestBody @Valid Task task) {
    return ResponseEntity.ok(service.save(task));
  }

  @GetMapping
  public ResponseEntity<List<Task>> getAssignedTasks(@RequestParam String assingeeId) {
    return ResponseEntity.ok(service.getAssingedTasks(assingeeId));
  }

  @GetMapping(value =  "/all", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<Task>> getAll() {
    return ResponseEntity.ok(service.getAll());
  }

  @PutMapping
  public ResponseEntity<Task> update(@RequestBody @Valid Task task) {
    return ResponseEntity.ok(service.update(task));
  }

  @PatchMapping
  public ResponseEntity<Task> updateStatus(@RequestBody @Valid UpdateTaskStatusRequest request) {
    return ResponseEntity.ok(service.updateStatus(request));
  }

  @PatchMapping("/assign/{id}")
  public ResponseEntity<Task> assignTask(@PathVariable String id, @RequestParam String contactId) {
    return ResponseEntity.ok(service.assignTask(id, contactId));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable String id) {
    service.delete(id);
    return ResponseEntity.noContent().build();
  }
}
