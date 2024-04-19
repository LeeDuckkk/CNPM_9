package com.example.cnpm.utils;

import com.example.cnpm.entity.User;
import com.example.cnpm.exception.CustomApiException;
import com.example.cnpm.repository.UserRepository;
import com.example.cnpm.security.CustomAuthentication;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SecurityContextUtil {

    private UserRepository userRepository;

    public SecurityContextUtil(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Long getCurrentUserId() {
        // user repo => get Id
        // Authention =>

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(authentication.getName()).
                orElseThrow(()-> new CustomApiException(HttpStatus.BAD_REQUEST,"Username not found"));


        CustomAuthentication principal = new CustomAuthentication(user.getId(),true, authentication.getAuthorities());

        return principal.getId();
    }
}