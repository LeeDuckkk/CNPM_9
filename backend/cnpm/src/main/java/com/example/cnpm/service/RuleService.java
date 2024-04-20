package com.example.cnpm.service;

import com.example.cnpm.controller.dtos.RuleDto;
import com.example.cnpm.entity.Rule;
import org.springframework.data.domain.Page;

public interface RuleService {
    Rule addRule(RuleDto rule);

    Rule updateRule(Long id, RuleDto rule);

    void deleteRule(Long id);

    Rule getRule(Long id);

    Page<RuleDto> getAllRule(int page);
}
