package com.example.cnpm.controller.dtos;

import lombok.Data;

@Data
public class PasswordChangeRequest {
    private String password;
    private String oldPassword;
}
