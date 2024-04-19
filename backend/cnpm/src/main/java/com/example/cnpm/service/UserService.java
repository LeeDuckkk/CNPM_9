package com.example.cnpm.service;

import com.example.cnpm.entity.User;

public interface UserService {
    User getCurrentUser();

    Long getCurrentUserId();
}
