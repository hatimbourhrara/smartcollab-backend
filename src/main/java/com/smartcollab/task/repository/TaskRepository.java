package com.smartcollab.task.repository;

import com.smartcollab.task.model.Task;
import com.smartcollab.task.model.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByStatus(TaskStatus status);

    List<Task> findByCreatedBy(String createdBy);
}