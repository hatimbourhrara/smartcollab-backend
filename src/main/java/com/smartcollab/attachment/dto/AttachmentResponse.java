package com.smartcollab.attachment.dto;

import com.smartcollab.attachment.model.Attachment;

import java.time.LocalDateTime;

public class AttachmentResponse {

    private Long id;
    private String fileName;
    private String fileType;
    private String uploadedBy;
    private Long taskId;
    private LocalDateTime uploadedAt;

    public AttachmentResponse(Attachment attachment) {
        this.id = attachment.getId();
        this.fileName = attachment.getFileName();
        this.fileType = attachment.getFileType();
        this.uploadedBy = attachment.getUploadedBy();
        this.uploadedAt = attachment.getUploadedAt();

        if (attachment.getTask() != null) {
            this.taskId = attachment.getTask().getId();
        }
    }

    public Long getId() {
        return id;
    }

    public String getFileName() {
        return fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public String getUploadedBy() {
        return uploadedBy;
    }

    public Long getTaskId() {
        return taskId;
    }

    public LocalDateTime getUploadedAt() {
        return uploadedAt;
    }
}