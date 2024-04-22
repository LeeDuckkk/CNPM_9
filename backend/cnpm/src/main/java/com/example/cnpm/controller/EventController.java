package com.example.cnpm.controller;

import com.example.cnpm.controller.dtos.EventDto;
import com.example.cnpm.entity.Event;
import com.example.cnpm.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/events")
public class EventController {

    @Autowired
    EventService eventService;

    @PostMapping("")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EventDto> createEvent(@RequestBody EventDto event) {
        return ResponseEntity.ok(new EventDto(eventService.addEvent(event)));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EventDto> updateEvent(@PathVariable Long id, @RequestBody EventDto event) {
        return ResponseEntity.ok(new EventDto(eventService.updateEvent(id, event)));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id) {
        eventService.deleteEvent(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EventDto> getEvent(@PathVariable Long id) {
        return ResponseEntity.ok(new EventDto(eventService.getEvent(id)));
    }

    @GetMapping("")
    public Page<Event> getAllEvents(@RequestParam int page) {
        return eventService.getAllEvent(page);
    }
}