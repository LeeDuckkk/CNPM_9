package com.example.cnpm.controller;

import com.example.cnpm.controller.dtos.UserDto;
import com.example.cnpm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @GetMapping("/users/{page}")
    Page<UserDto> getListUser(@PathVariable("page") int page) {
        return userService.getAllUser(page);
    }
}
