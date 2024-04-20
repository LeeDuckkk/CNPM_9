package com.example.cnpm.controller.dtos;

import com.example.cnpm.entity.Rule;
import lombok.Data;

@Data
public class RuleDto {
    private String name;
    private String description;

    public RuleDto(Rule rule) {
        this.name = rule.getName();
        this.description = rule.getDescription();
    }
}
