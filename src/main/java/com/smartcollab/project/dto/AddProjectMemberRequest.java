package com.smartcollab.project.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class AddProjectMemberRequest {

    @NotBlank
    @Email
    private String userEmail;

    public String getUserEmail() {
        return userEmail;
    }
}