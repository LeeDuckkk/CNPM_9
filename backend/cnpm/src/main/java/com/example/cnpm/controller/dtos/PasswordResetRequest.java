package com.example.cnpm.controller.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PasswordResetRequest {
    @NotNull
    String email;
}
