package com.smartcollab.activity.dto;

import com.smartcollab.activity.model.ActivityLog;

import java.time.LocalDateTime;

public class ActivityLogResponse {

    private Long id;
    private String action;
    private String userEmail;
    private LocalDateTime createdAt;

    public ActivityLogResponse(ActivityLog activityLog) {
        this.id = activityLog.getId();
        this.action = activityLog.getAction();
        this.userEmail = activityLog.getUserEmail();
        this.createdAt = activityLog.getCreatedAt();
    }

    public Long getId() {
        return id;
    }

    public String getAction() {
        return action;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}