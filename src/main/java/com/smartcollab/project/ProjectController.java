package com.smartcollab.project;

import com.smartcollab.project.dto.CreateProjectRequest;
import com.smartcollab.project.dto.ProjectResponse;
import com.smartcollab.project.model.Project;
import com.smartcollab.project.service.ProjectService;
import com.smartcollab.security.JwtService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    private final ProjectService projectService;
    private final JwtService jwtService;

    public ProjectController(
            ProjectService projectService,
            JwtService jwtService
    ) {
        this.projectService = projectService;
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
}