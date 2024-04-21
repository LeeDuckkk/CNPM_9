package com.example.cnpm.controller;

import com.example.cnpm.controller.dtos.EditProfileRequest;
import com.example.cnpm.controller.dtos.PasswordResetRequest;
import com.example.cnpm.controller.dtos.UserDto;
import com.example.cnpm.entity.User;
import com.example.cnpm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/me")
    public ResponseEntity<UserDto> getCurrentUser(){
        return new ResponseEntity<>(new UserDto(userService.getCurrentUser()), HttpStatus.OK);
    }

    @GetMapping("/me/id")
    public ResponseEntity<Long> getCurrentId(){
        Long currentId = userService.getCurrentUserId();
        return new ResponseEntity<>( currentId,HttpStatus.OK);
    }

    @PostMapping("/edit-profile")
    public ResponseEntity<UserDto> editProfile(@RequestBody EditProfileRequest editProfileRequest) {
        return new ResponseEntity<>(new UserDto(userService.editProfile(editProfileRequest)), HttpStatus.OK);
    }
}
