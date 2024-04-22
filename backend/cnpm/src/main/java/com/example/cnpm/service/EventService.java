package com.example.cnpm.service;

import com.example.cnpm.controller.dtos.EventDto;
import com.example.cnpm.entity.Event;
import org.springframework.data.domain.Page;

public interface EventService {
    Event addEvent(EventDto event);

    Event updateEvent(Long id, EventDto event);

    void deleteEvent(Long id);

    Event getEvent(Long id);

    Page<Event> getAllEvent(int page);
}