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
import java.util.stream.Collectors;

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
        List<EventDto> eventDtos = eventRepository.findAllDesc().stream().map(EventDto::new).collect(Collectors.toList());
        int pageSize = 10;
        int totalElements = eventDtos.size();
        int totalPage = (int) Math.ceil((double) totalElements / pageSize);
        if (page > totalPage) {
            page = totalPage;
        }
        if (page <= 0) {
            page = 1;
        }
        int start = (page - 1) * pageSize;
        int end = Math.min(start + pageSize, totalElements);
        return new PageImpl<>(eventDtos.subList(start, end), PageRequest.of(page - 1, pageSize), totalElements);
    }
}