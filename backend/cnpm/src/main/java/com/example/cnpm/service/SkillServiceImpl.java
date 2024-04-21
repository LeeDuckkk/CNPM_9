package com.example.cnpm.service;

import com.example.cnpm.controller.dtos.SkillDto;
import com.example.cnpm.controller.dtos.UserDto;
import com.example.cnpm.entity.Skill;
import com.example.cnpm.repository.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public Page<SkillDto> getAllSkill(int page) {
        List<SkillDto> skillDtos = skillRepository.findAllDesc().stream().map(SkillDto::new).collect(Collectors.toList());
        int pageSize = 10;
        int totalElements = skillDtos.size();
        int totalPage = (int) Math.ceil((double) totalElements / pageSize);
        if (page > totalPage) {
            page = totalPage;
        }
        if (page <= 0) {
            page = 1;
        }
        int start = (page - 1) * pageSize;
        int end = Math.min(start + pageSize, totalElements);
        return new PageImpl<>(skillDtos.subList(start, end), PageRequest.of(page - 1, pageSize), totalElements);
    }
}