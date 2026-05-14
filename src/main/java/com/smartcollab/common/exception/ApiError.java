package com.smartcollab.common.exception;

import java.time.LocalDateTime;

public class ApiError {

    private String message;
    private int status;
    private String path;
    private LocalDateTime timestamp;

    public ApiError(String message, int status, String path) {
        this.message = message;
        this.status = status;
        this.path = path;
        this.timestamp = LocalDateTime.now();
    }

    public String getMessage() {
        return message;
    }

    public int getStatus() {
        return status;
    }

    public String getPath() {
        return path;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}