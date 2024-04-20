package com.example.cnpm.service;

import com.example.cnpm.controller.dtos.SkillDto;
import com.example.cnpm.entity.Skill;

public interface SkillService {
    Skill addSkill(SkillDto skill);

    Skill updateSkill(Long id, SkillDto skill);

    void deleteSkill(Long id);

    Skill getSkill(Long id);
}