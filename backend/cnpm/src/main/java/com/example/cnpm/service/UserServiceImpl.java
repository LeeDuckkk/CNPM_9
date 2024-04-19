package com.example.cnpm.service;

import com.example.cnpm.entity.User;
import com.example.cnpm.repository.UserRepository;
import com.example.cnpm.utils.SecurityContextUtil;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    private SecurityContextUtil securityContextUtil;

    public UserServiceImpl(UserRepository userRepository, SecurityContextUtil securityContextUtil) {
        this.userRepository = userRepository;
        this.securityContextUtil = securityContextUtil;
    }

    @Override
    public User getCurrentUser() {
        Long currentUserId = getCurrentUserId();
        if (currentUserId != null) {
            return userRepository.getReferenceById(currentUserId);
        }
        throw new RuntimeException("User ID is null");
    }

    @Override
    public Long getCurrentUserId() {
        return securityContextUtil.getCurrentUserId();
    }
}
