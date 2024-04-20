package com.example.cnpm.service;

import com.example.cnpm.controller.dtos.RuleDto;
import com.example.cnpm.entity.Rule;
import com.example.cnpm.repository.RuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
