package com.example.cnpm.service;

import com.example.cnpm.controller.dtos.ConfessionDto;
import com.example.cnpm.controller.dtos.EditProfileRequest;
import com.example.cnpm.controller.dtos.UserDto;
import com.example.cnpm.entity.Confession;
import com.example.cnpm.entity.User;
import org.springframework.data.domain.Page;

public interface UserService {
    User getCurrentUser();

    Long getCurrentUserId();

    User findByEmail(String email);

    User editProfile(EditProfileRequest editProfileRequest);

    Page<UserDto> getAllUser(int page);
}
