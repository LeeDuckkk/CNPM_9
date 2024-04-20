package com.example.cnpm.service;

import com.example.cnpm.controller.dtos.EventDto;
import com.example.cnpm.entity.Event;

public interface EventService {
    Event addEvent(EventDto event);

    Event updateEvent(Long id, EventDto event);

    void deleteEvent(Long id);

    Event getEvent(Long id);
}