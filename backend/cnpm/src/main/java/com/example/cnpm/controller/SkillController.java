package com.example.cnpm.controller;

import com.example.cnpm.controller.dtos.SkillDto;
import com.example.cnpm.entity.Skill;
import com.example.cnpm.service.SkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/skills")
public class SkillController {

    @Autowired
    SkillService skillService;

    @PostMapping("")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SkillDto> createSkill(@RequestBody SkillDto skill) {
        return ResponseEntity.ok(new SkillDto(skillService.addSkill(skill)));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SkillDto> updateSkill(@PathVariable Long id, @RequestBody SkillDto skill) {
        Skill updatedSkill = skillService.updateSkill(id, skill);
        return ResponseEntity.ok(new SkillDto(updatedSkill));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteSkill(@PathVariable Long id) {
        skillService.deleteSkill(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SkillDto> getSkill(@PathVariable Long id) {
        return ResponseEntity.ok(new SkillDto(skillService.getSkill(id)));
    }

    @GetMapping("")
    public Page<SkillDto> getAllSkills(@RequestParam int page) {
        return skillService.getAllSkill(page);
    }
}