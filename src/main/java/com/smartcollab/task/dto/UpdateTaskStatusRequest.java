package com.smartcollab.task.dto;

import com.smartcollab.task.model.TaskStatus;

public class UpdateTaskStatusRequest {

    private TaskStatus status;

    public TaskStatus getStatus() {
        return status;
    }
}