package com.example.cnpm.service;

import com.example.cnpm.controller.dtos.LoginDto;
import com.example.cnpm.controller.dtos.RegisterDto;
import com.example.cnpm.controller.dtos.UserDto;
import com.example.cnpm.entity.User;

public interface AuthService {
    String login(LoginDto loginDto);

    UserDto changePassword(UserDto user);

    User register(RegisterDto registerDto);
}
