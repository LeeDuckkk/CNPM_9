package com.example.cnpm.service;

import com.example.cnpm.controller.dtos.EventDto;
import com.example.cnpm.entity.Event;
import com.example.cnpm.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventServiceImpl implements EventService {

    @Autowired
    EventRepository eventRepository;

    @Override
    public Event addEvent(EventDto eventDto) {
        Event event = new Event();
        event.setName(eventDto.getName());
        event.setContent(eventDto.getContent());
        return eventRepository.save(event);
    }

    @Override
    public Event updateEvent(Long id, EventDto eventDto) {
        Event event = eventRepository.findById(id).orElse(null);
        if (event == null) {
            return null;
        }
        event.setName(eventDto.getName());
        event.setContent(eventDto.getContent());
        return eventRepository.save(event);
    }

    @Override
    public void deleteEvent(Long id) {
        Event event = eventRepository.findById(id).orElse(null);
        if (event == null) {
            return;
        }
        eventRepository.delete(event);
    }

    @Override
    public Event getEvent(Long id) {
        return eventRepository.findById(id).orElse(null);
    }
}