package com.smartcollab.project.dto;

import com.smartcollab.project.model.ProjectMember;

public class ProjectMemberResponse {

    private Long id;
    private String userEmail;
    private Long projectId;

    public ProjectMemberResponse(ProjectMember member) {
        this.id = member.getId();
        this.userEmail = member.getUserEmail();

        if (member.getProject() != null) {
            this.projectId = member.getProject().getId();
        }
    }

    public Long getId() {
        return id;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public Long getProjectId() {
        return projectId;
    }
}