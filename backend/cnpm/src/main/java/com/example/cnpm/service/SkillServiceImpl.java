package com.example.cnpm.service;

import com.example.cnpm.controller.dtos.SkillDto;
import com.example.cnpm.entity.Skill;
import com.example.cnpm.repository.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SkillServiceImpl implements SkillService {

    @Autowired
    SkillRepository skillRepository;

    @Override
    public Skill addSkill(SkillDto skillDto) {
        Skill skill = new Skill();
        skill.setName(skillDto.getName());
        skill.setDescription(skillDto.getDescription());
        return skillRepository.save(skill);
    }

    @Override
    public Skill updateSkill(Long id, SkillDto skillDto) {
        Skill skill = skillRepository.findById(id).orElse(null);
        if (skill == null) {
            return null;
        }
        skill.setName(skillDto.getName());
        skill.setDescription(skillDto.getDescription());
        return skillRepository.save(skill);
    }

    @Override
    public void deleteSkill(Long id) {
        Skill skill = skillRepository.findById(id).orElse(null);
        if (skill == null) {
            return;
        }
        skillRepository.delete(skill);
    }

    @Override
    public Skill getSkill(Long id) {
        return skillRepository.findById(id).orElse(null);
    }
}