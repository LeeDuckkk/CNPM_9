package com.example.cnpm.service;

import com.example.cnpm.controller.dtos.EventDto;
import com.example.cnpm.controller.dtos.UserDto;
import com.example.cnpm.entity.Event;
import com.example.cnpm.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public Page<EventDto> getAllEvent(int page) {
        List<EventDto> eventDtos = eventRepository.findAllDesc().stream().map(EventDto::new).toList();
        int pageSize = 10;
        int totalPage = (int) Math.ceil(eventDtos.size() / (double) pageSize);
        if (page > totalPage) {
            page = totalPage;
        }
        if (page <= 0) {
            page = 1;
        }
        int start = (page - 1) * pageSize;
        int end = Math.min(start + pageSize, eventDtos.size());
        return new PageImpl<>(eventDtos.subList(start, end), PageRequest.of(page, pageSize), eventDtos.size());
    }
}