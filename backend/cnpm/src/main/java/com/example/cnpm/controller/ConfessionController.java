package com.example.cnpm.controller;

import com.example.cnpm.controller.dtos.ConfessionDto;
import com.example.cnpm.entity.Confession;
import com.example.cnpm.service.ConfessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/confessions")
public class ConfessionController {

    @Autowired
    ConfessionService confessionService;

    @PostMapping("")
    public ResponseEntity<Confession> createConfession(@ModelAttribute ConfessionDto confession) {
        return ResponseEntity.ok(confessionService.addConfession(confession));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Confession> updateConfession(@PathVariable Long id, @ModelAttribute ConfessionDto confession) {
        Confession updatedConfession = confessionService.updateConfession(id, confession);
        return ResponseEntity.ok(updatedConfession);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteConfession(@PathVariable Long id) {
        confessionService.deleteConfession(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Confession> getConfession(@PathVariable Long id) {
        Confession confession = confessionService.getConfession(id);
        return ResponseEntity.ok(confession);
    }
}
