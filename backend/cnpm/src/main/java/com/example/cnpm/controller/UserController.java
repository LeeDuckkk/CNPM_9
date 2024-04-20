package com.example.cnpm.controller;

import com.example.cnpm.entity.User;
import com.example.cnpm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/me")
    public ResponseEntity<User> getCurrentUser(){
        return new ResponseEntity<>(userService.getCurrentUser(), HttpStatus.OK);
    }
    @GetMapping("/me/id")
    public ResponseEntity<Long> getCurrentId(){
        Long currentId = userService.getCurrentUserId();
        return new ResponseEntity<>( currentId,HttpStatus.OK);
    }
}
