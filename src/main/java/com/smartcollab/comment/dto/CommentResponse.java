package com.smartcollab.comment.dto;

import com.smartcollab.comment.model.Comment;

import java.time.LocalDateTime;

public class CommentResponse {

    private Long id;
    private String content;
    private String createdBy;
    private Long taskId;
    private LocalDateTime createdAt;

    public CommentResponse(Comment comment) {
        this.id = comment.getId();
        this.content = comment.getContent();
        this.createdBy = comment.getCreatedBy();
        this.createdAt = comment.getCreatedAt();

        if (comment.getTask() != null) {
            this.taskId = comment.getTask().getId();
        }
    }

    public Long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public Long getTaskId() {
        return taskId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}