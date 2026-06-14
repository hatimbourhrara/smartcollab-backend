package com.smartcollab.project.service;

import com.smartcollab.common.exception.ResourceNotFoundException;
import com.smartcollab.project.model.Project;
import com.smartcollab.project.model.ProjectMember;
import com.smartcollab.project.repository.ProjectMemberRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectMemberService {

    private final ProjectMemberRepository projectMemberRepository;
    private final ProjectService projectService;

    public ProjectMemberService(
            ProjectMemberRepository projectMemberRepository,
            ProjectService projectService
    ) {
        this.projectMemberRepository = projectMemberRepository;
        this.projectService = projectService;
    }

    public ProjectMember addMember(
            Long projectId,
            String memberEmail,
            String ownerEmail
    ) {
        Project project = projectService.getUserProjectById(projectId, ownerEmail);

        ProjectMember member = new ProjectMember();
        member.setUserEmail(memberEmail);
        member.setProject(project);

        return projectMemberRepository.save(member);
    }

    public List<ProjectMember> getProjectMembers(
            Long projectId,
            String ownerEmail
    ) {
        Project project = projectService.getUserProjectById(projectId, ownerEmail);

        return projectMemberRepository.findByProject(project);
    }

    public void removeMember(
            Long projectId,
            Long memberId,
            String ownerEmail
    ) {
        Project project = projectService.getUserProjectById(projectId, ownerEmail);

        ProjectMember member = projectMemberRepository
                .findByIdAndProject(memberId, project)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Project member not found")
                );

        projectMemberRepository.delete(member);
    }
}
