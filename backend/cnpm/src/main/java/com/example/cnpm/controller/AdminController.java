package com.example.cnpm.controller;

import com.example.cnpm.controller.dtos.*;
import com.example.cnpm.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private ConfessionService confessionService;

    @Autowired
    private RuleService ruleService;

    @Autowired
    private EventService eventService;

    @Autowired
    private SkillService skillService;


    @GetMapping("/users/{page}")
    @PreAuthorize("hasRole('ADMIN')")
    Page<UserDto> getListUser(@PathVariable("page") int page) {
        return userService.getAllUser(page);
    }

    @GetMapping("/confessions/{page}")
    @PreAuthorize("hasRole('ADMIN')")
    Page<ConfessionDto> getListConfession(@PathVariable("page") int page) {
        return confessionService.getAllConfession(page);
    }

    @GetMapping("/rules/{page}")
    @PreAuthorize("hasRole('ADMIN')")
    Page<RuleDto> getListRule(@PathVariable("page") int page) {
        return ruleService.getAllRule(page);
    }

    @GetMapping("/events/{page}")
    @PreAuthorize("hasRole('ADMIN')")
    Page<EventDto> getListEvent(@PathVariable("page") int page) {
        return eventService.getAllEvent(page);
    }

    @GetMapping("/skills/{page}")
    @PreAuthorize("hasRole('ADMIN')")
    Page<SkillDto> getListSkill(@PathVariable("page") int page) {
        return skillService.getAllSkill(page);
    }

    @PostMapping("/accept-confession/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> acceptConfession(@PathVariable Long id) {
        return ResponseEntity.ok(confessionService.acceptConfession(id));
    }
}
