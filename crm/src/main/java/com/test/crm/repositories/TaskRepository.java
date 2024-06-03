package com.test.crm.repositories;

import com.test.crm.models.task.Task;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, String> {

  List<Task> findAllByAssigneeId(String assignee);

  List<Task> findAllByDeadLineBeforeAndDeadLineNotifiedIsFalse(LocalDateTime deadLine, Pageable pageable);
}
