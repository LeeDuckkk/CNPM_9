package com.example.cnpm.controller;

import com.example.cnpm.controller.dtos.RuleDto;
import com.example.cnpm.entity.Rule;
import com.example.cnpm.service.RuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/rules")
public class RuleController {

    @Autowired
    RuleService ruleService;

    @PostMapping("")
    public ResponseEntity<RuleDto> createRule(@RequestBody RuleDto rule) {
        Rule ruleEntity = ruleService.addRule(rule);
        return ResponseEntity.ok(new RuleDto(ruleEntity));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RuleDto> updateRule(@PathVariable Long id, @RequestBody RuleDto rule) {
        Rule updatedRule = ruleService.updateRule(id, rule);
        return ResponseEntity.ok(new RuleDto(updatedRule));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRule(@PathVariable Long id) {
        ruleService.deleteRule(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<RuleDto> getRule(@PathVariable Long id) {
        return ResponseEntity.ok(new RuleDto(ruleService.getRule(id)));
    }
}
