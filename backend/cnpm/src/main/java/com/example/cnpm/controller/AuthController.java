package com.example.cnpm.controller;

import com.example.cnpm.controller.dtos.*;
import com.example.cnpm.entity.User;
import com.example.cnpm.service.AuthService;
import com.example.cnpm.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private AuthService authService;
    private UserService userService;

    public AuthController(AuthService authService, UserService userService) {
        this.authService = authService;
        this.userService = userService;
    }

    @PostMapping("/signin")
    public ResponseEntity<JwtAuthResponse> login(@RequestBody LoginDto loginDto){
        String token = authService.login(loginDto);

        JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
        jwtAuthResponse.setAccessToken(token);
        return new ResponseEntity<>(jwtAuthResponse, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody RegisterDto registerDto){
        User user = authService.register(registerDto);
        return new ResponseEntity<>(user,HttpStatus.CREATED);
    }

    @PostMapping("/change-password-by-email")
    public ResponseEntity<?> changePasswordByEmail(@RequestBody PasswordResetRequest passwordResetRequest){
        User user = userService.findByEmail(passwordResetRequest.getEmail());
        if (user == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        authService.changePasswordByEmail(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/change-password-by-old-password")
    public ResponseEntity<?> changePasswordByOldPassword(@RequestBody PasswordChangeRequest passwordChangeRequest){
        String oldPassword = passwordChangeRequest.getOldPassword();
        String newPassword = passwordChangeRequest.getNewPassword();

        User user = userService.getCurrentUser();
        if (user == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        authService.changePasswordByOldPassword(user, oldPassword, newPassword);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
