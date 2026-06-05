package com.smartcollab.task.dto;

import com.smartcollab.task.model.Task;
import com.smartcollab.task.model.TaskStatus;

import java.time.LocalDateTime;

public class TaskResponse {

    private Long id;
    private String title;
    private String description;
    private TaskStatus status;
    private String createdBy;
    private Long projectId;
    private LocalDateTime createdAt;

    public TaskResponse(Task task) {
        this.id = task.getId();
        this.title = task.getTitle();
        this.description = task.getDescription();
        this.status = task.getStatus();
        this.createdBy = task.getCreatedBy();
        this.createdAt = task.getCreatedAt();

        if (task.getProject() != null) {
            this.projectId = task.getProject().getId();
        }
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public Long getProjectId() {
        return projectId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}