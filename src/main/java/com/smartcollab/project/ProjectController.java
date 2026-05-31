package com.smartcollab.project;

import com.smartcollab.project.dto.CreateProjectRequest;
import com.smartcollab.project.model.Project;
import com.smartcollab.project.service.ProjectService;
import com.smartcollab.security.JwtService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public Project createProject(
            @Valid @RequestBody CreateProjectRequest request,
            @RequestHeader("Authorization") String authHeader
    ) {

        String token = authHeader.replace("Bearer ", "");
        String userEmail = jwtService.extractEmail(token);

        return projectService.createProject(
                request.getName(),
                request.getDescription(),
                userEmail
        );
    }

    @GetMapping("/my")
    public List<Project> getMyProjects(
            @RequestHeader("Authorization") String authHeader
    ) {

        String token = authHeader.replace("Bearer ", "");
        String userEmail = jwtService.extractEmail(token);

        return projectService.getMyProjects(userEmail);
    }
}