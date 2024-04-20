package com.example.cnpm.controller.dtos;

import com.example.cnpm.entity.Event;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventDto {
    private String name;
    private String content;

    public EventDto(Event event) {
        this.name = event.getName();
        this.content = event.getContent();
    }
}