package com.smartcollab.task.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CreateTaskRequest {

    @NotBlank
    @Size(min = 3, max = 100)
    private String title;

    @Size(max = 500)
    private String description;

    @NotNull(message = "Project id is required")
    private Long projectId;

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Long getProjectId() {
        return projectId;
    }
}