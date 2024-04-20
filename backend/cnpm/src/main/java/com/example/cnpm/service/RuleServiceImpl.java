package com.example.cnpm.service;

import com.example.cnpm.controller.dtos.RuleDto;
import com.example.cnpm.controller.dtos.UserDto;
import com.example.cnpm.entity.Rule;
import com.example.cnpm.repository.RuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RuleServiceImpl implements RuleService {

    @Autowired
    RuleRepository ruleRepository;

    @Override
    public Rule addRule(RuleDto ruleDto) {
        Rule rule = new Rule();
        rule.setName(ruleDto.getName());
        rule.setDescription(ruleDto.getDescription());
        return ruleRepository.save(rule);
    }

    @Override
    public Rule updateRule(Long id, RuleDto ruleDto) {
        Rule rule = ruleRepository.findById(id).orElse(null);
        if (rule == null) {
            return null;
        }
        rule.setName(ruleDto.getName());
        rule.setDescription(ruleDto.getDescription());
        return ruleRepository.save(rule);
    }

    @Override
    public void deleteRule(Long id) {
        Rule rule = ruleRepository.findById(id).orElse(null);
        if (rule == null) {
            return;
        }
        ruleRepository.delete(rule);
    }

    @Override
    public Rule getRule(Long id) {
        return ruleRepository.findById(id).orElse(null);
    }

    @Override
    public Page<RuleDto> getAllRule(int page) {
        List<RuleDto> ruleDtos = ruleRepository.findAllDesc().stream().map(RuleDto::new).toList();
        int pageSize = 10;
        int totalPage = (int) Math.ceil(ruleDtos.size() / (double) pageSize);
        if (page > totalPage) {
            page = totalPage;
        }
        if (page <= 0) {
            page = 1;
        }
        int start = (page - 1) * pageSize;
        int end = Math.min(start + pageSize, ruleDtos.size());
        return new PageImpl<>(ruleDtos.subList(start, end), PageRequest.of(page, pageSize), ruleDtos.size());
    }
}
