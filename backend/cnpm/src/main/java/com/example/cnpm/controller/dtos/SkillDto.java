package com.example.cnpm.controller.dtos;

import com.example.cnpm.entity.Skill;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SkillDto {
    private String name;
    private String description;

    public SkillDto(Skill skill) {
        this.name = skill.getName();
        this.description = skill.getDescription();
    }
}