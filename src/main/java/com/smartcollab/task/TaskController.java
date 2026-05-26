package com.smartcollab.task;

import com.smartcollab.security.JwtService;
import com.smartcollab.task.dto.CreateTaskRequest;
import com.smartcollab.task.dto.UpdateTaskRequest;
import com.smartcollab.task.dto.UpdateTaskStatusRequest;
import com.smartcollab.task.model.Task;
import com.smartcollab.task.model.TaskStatus;
import com.smartcollab.task.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;
    private final JwtService jwtService;

    public TaskController(
            TaskService taskService,
            JwtService jwtService
    ) {
        this.taskService = taskService;
        this.jwtService = jwtService;
    }

    @PostMapping
    public Task createTask(
            @Valid @RequestBody CreateTaskRequest request,
            @RequestHeader("Authorization") String authHeader
    ) {
        String token = authHeader.replace("Bearer ", "");
        String userEmail = jwtService.extractEmail(token);

        return taskService.createTask(request, userEmail);
    }

    @GetMapping
    public List<Task> getAllTasks() {
        return taskService.getAllTasks();
    }

    @GetMapping("/status/{status}")
    public List<Task> getTasksByStatus(@PathVariable TaskStatus status) {
        return taskService.getTasksByStatus(status);
    }

    @GetMapping("/{id}")
    public Task getTaskById(@PathVariable Long id) {
        return taskService.getTaskById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
    }

    @PutMapping("/{id}")
    public Task updateTask(
            @PathVariable Long id,
            @Valid @RequestBody UpdateTaskRequest request
    ) {
        return taskService.updateTask(id, request);
    }

    @PatchMapping("/{id}/status")
    public Task updateTaskStatus(
            @PathVariable Long id,
            @Valid @RequestBody UpdateTaskStatusRequest request
    ) {
        return taskService.updateTaskStatus(id, request);
    }
}