package com.smartcollab.project;

import com.smartcollab.project.dto.CreateProjectRequest;
import com.smartcollab.project.dto.ProjectResponse;
import com.smartcollab.project.dto.UpdateProjectRequest;
import com.smartcollab.project.model.Project;
import com.smartcollab.project.service.ProjectService;
import com.smartcollab.security.JwtService;
import com.smartcollab.task.dto.TaskResponse;
import com.smartcollab.task.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    private final ProjectService projectService;
    private final TaskService taskService;
    private final JwtService jwtService;

    public ProjectController(
            ProjectService projectService,
            TaskService taskService,
            JwtService jwtService
    ) {
        this.projectService = projectService;
        this.taskService = taskService;
        this.jwtService = jwtService;
    }

    @PostMapping
    public ProjectResponse createProject(
            @Valid @RequestBody CreateProjectRequest request,
            @RequestHeader("Authorization") String authHeader
    ) {
        String token = authHeader.replace("Bearer ", "");
        String userEmail = jwtService.extractEmail(token);

        Project project = projectService.createProject(
                request.getName(),
                request.getDescription(),
                userEmail
        );

        return new ProjectResponse(project);
    }

    @GetMapping("/my")
    public List<ProjectResponse> getMyProjects(
            @RequestHeader("Authorization") String authHeader
    ) {
        String token = authHeader.replace("Bearer ", "");
        String userEmail = jwtService.extractEmail(token);

        return projectService.getMyProjects(userEmail)
                .stream()
                .map(ProjectResponse::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ProjectResponse getProjectById(
            @PathVariable Long id,
            @RequestHeader("Authorization") String authHeader
    ) {
        String token = authHeader.replace("Bearer ", "");
        String userEmail = jwtService.extractEmail(token);

        return new ProjectResponse(
                projectService.getUserProjectById(id, userEmail)
        );
    }

    @GetMapping("/{id}/tasks")
    public List<TaskResponse> getProjectTasks(
            @PathVariable Long id,
            @RequestHeader("Authorization") String authHeader
    ) {
        String token = authHeader.replace("Bearer ", "");
        String userEmail = jwtService.extractEmail(token);

        projectService.getUserProjectById(id, userEmail);

        return taskService.getTasksByProject(id)
                .stream()
                .map(TaskResponse::new)
                .collect(Collectors.toList());
    }

    @PutMapping("/{id}")
    public ProjectResponse updateProject(
            @PathVariable Long id,
            @RequestBody UpdateProjectRequest request,
            @RequestHeader("Authorization") String authHeader
    ) {
        String token = authHeader.replace("Bearer ", "");
        String userEmail = jwtService.extractEmail(token);

        Project project = projectService.updateProject(
                id,
                request,
                userEmail
        );

        return new ProjectResponse(project);
    }

    @DeleteMapping("/{id}")
    public void deleteProject(
            @PathVariable Long id,
            @RequestHeader("Authorization") String authHeader
    ) {
        String token = authHeader.replace("Bearer ", "");
        String userEmail = jwtService.extractEmail(token);

        projectService.deleteProject(id, userEmail);
    }
}