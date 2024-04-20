package com.example.cnpm.controller.dtos;

import lombok.Data;

@Data
public class PasswordChangeRequest {
    private String newPassword;
    private String oldPassword;
}
