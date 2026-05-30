package com.smartcollab.task.service;

import com.smartcollab.common.exception.ResourceNotFoundException;
import com.smartcollab.task.dto.CreateTaskRequest;
import com.smartcollab.task.dto.UpdateTaskRequest;
import com.smartcollab.task.dto.UpdateTaskStatusRequest;
import com.smartcollab.task.model.Task;
import com.smartcollab.task.model.TaskStatus;
import com.smartcollab.task.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Task createTask(CreateTaskRequest request, String userEmail) {
        Task task = new Task();

        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setCreatedBy(userEmail);

        return taskRepository.save(task);
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public List<Task> getTasksByStatus(TaskStatus status) {
        return taskRepository.findByStatus(status);
    }

    public List<Task> getTasksByCreator(String userEmail) {
        return taskRepository.findByCreatedBy(userEmail);
    }

    public Task getTaskById(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Task not found")
                );
    }

    public Task getUserTaskById(Long id, String userEmail) {
        Task task = getTaskById(id);

        if (!task.getCreatedBy().equals(userEmail)) {
            throw new RuntimeException("You are not allowed to access this task");
        }

        return task;
    }

    public void deleteTask(Long id, String userEmail) {
        Task task = getUserTaskById(id, userEmail);

        taskRepository.delete(task);
    }

    public Task updateTask(Long id, UpdateTaskRequest request, String userEmail) {
        Task task = getUserTaskById(id, userEmail);

        if (request.getTitle() != null) {
            task.setTitle(request.getTitle());
        }

        if (request.getDescription() != null) {
            task.setDescription(request.getDescription());
        }

        return taskRepository.save(task);
    }

    public Task updateTaskStatus(Long id, UpdateTaskStatusRequest request, String userEmail) {
        Task task = getUserTaskById(id, userEmail);

        task.setStatus(request.getStatus());

        return taskRepository.save(task);
    }
}