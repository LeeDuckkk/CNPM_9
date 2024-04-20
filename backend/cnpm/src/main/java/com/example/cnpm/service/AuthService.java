package com.example.cnpm.service;

import com.example.cnpm.controller.dtos.LoginDto;
import com.example.cnpm.controller.dtos.RegisterDto;
import com.example.cnpm.entity.User;

public interface AuthService {
    String login(LoginDto loginDto);

    void changePasswordByEmail(User user);

    void changePasswordByOldPassword(User user, String oldPassword, String newPassword);

    User register(RegisterDto registerDto);
}
