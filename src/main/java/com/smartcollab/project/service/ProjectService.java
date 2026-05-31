package com.smartcollab.project.service;

import com.smartcollab.common.exception.ResourceNotFoundException;
import com.smartcollab.project.model.Project;
import com.smartcollab.project.repository.ProjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public Project createProject(
            String name,
            String description,
            String userEmail
    ) {

        Project project = new Project();

        project.setName(name);
        project.setDescription(description);
        project.setCreatedBy(userEmail);

        return projectRepository.save(project);
    }

    public List<Project> getMyProjects(String userEmail) {
        return projectRepository.findByCreatedBy(userEmail);
    }

    public Project getProjectById(Long id) {

        return projectRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Project not found")
                );
    }
}