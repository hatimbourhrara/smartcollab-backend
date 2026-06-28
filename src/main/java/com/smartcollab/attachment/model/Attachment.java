package com.smartcollab.attachment.model;

import com.smartcollab.task.model.Task;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "attachments")
public class Attachment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fileName;

    private String filePath;

    private String fileType;

    private String uploadedBy;

    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;

    private LocalDateTime uploadedAt = LocalDateTime.now();

    public Long getId() {
        return id;
    }

    public String getFileName() {
        return fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public String getFileType() {
        return fileType;
    }

    public String getUploadedBy() {
        return uploadedBy;
    }

    public Task getTask() {
        return task;
    }

    public LocalDateTime getUploadedAt() {
        return uploadedAt;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public void setUploadedBy(String uploadedBy) {
        this.uploadedBy = uploadedBy;
    }

    public void setTask(Task task) {
        this.task = task;
    }
}