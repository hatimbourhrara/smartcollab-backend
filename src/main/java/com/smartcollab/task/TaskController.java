package com.smartcollab.task;

import com.smartcollab.security.JwtService;
import com.smartcollab.task.dto.CreateTaskRequest;
import com.smartcollab.task.dto.TaskResponse;
import com.smartcollab.task.dto.UpdateTaskRequest;
import com.smartcollab.task.dto.UpdateTaskStatusRequest;
import com.smartcollab.task.model.Task;
import com.smartcollab.task.model.TaskStatus;
import com.smartcollab.task.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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

    @GetMapping("/my")
    public List<TaskResponse> getMyTasks(
            @RequestHeader("Authorization") String authHeader
    ) {

        String token = authHeader.replace("Bearer ", "");
        String userEmail = jwtService.extractEmail(token);

        return taskService.getTasksByCreator(userEmail)
                .stream()
                .map(TaskResponse::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/status/{status}")
    public List<Task> getTasksByStatus(@PathVariable TaskStatus status) {
        return taskService.getTasksByStatus(status);
    }

    @GetMapping("/{id}")
    public TaskResponse getTaskById(
            @PathVariable Long id,
            @RequestHeader("Authorization") String authHeader
    ) {

        String token = authHeader.replace("Bearer ", "");
        String userEmail = jwtService.extractEmail(token);

        return new TaskResponse(
                taskService.getUserTaskById(id, userEmail)
        );
    }

    @DeleteMapping("/{id}")
    public void deleteTask(
            @PathVariable Long id,
            @RequestHeader("Authorization") String authHeader
    ) {

        String token = authHeader.replace("Bearer ", "");
        String userEmail = jwtService.extractEmail(token);

        taskService.deleteTask(id, userEmail);
    }

    @PutMapping("/{id}")
    public Task updateTask(
            @PathVariable Long id,
            @Valid @RequestBody UpdateTaskRequest request,
            @RequestHeader("Authorization") String authHeader
    ) {

        String token = authHeader.replace("Bearer ", "");
        String userEmail = jwtService.extractEmail(token);

        return taskService.updateTask(id, request, userEmail);
    }

    @PatchMapping("/{id}/status")
    public Task updateTaskStatus(
            @PathVariable Long id,
            @Valid @RequestBody UpdateTaskStatusRequest request,
            @RequestHeader("Authorization") String authHeader
    ) {

        String token = authHeader.replace("Bearer ", "");
        String userEmail = jwtService.extractEmail(token);

        return taskService.updateTaskStatus(id, request, userEmail);
    }
}