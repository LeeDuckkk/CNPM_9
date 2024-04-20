package com.example.cnpm.controller;

import com.example.cnpm.controller.dtos.CommentDto;
import com.example.cnpm.controller.dtos.CommentRequest;
import com.example.cnpm.controller.dtos.ConfessionDto;
import com.example.cnpm.entity.Comment;
import com.example.cnpm.entity.Confession;
import com.example.cnpm.entity.User;
import com.example.cnpm.service.CommentService;
import com.example.cnpm.service.ConfessionService;
import com.example.cnpm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/confessions")
public class ConfessionController {

    @Autowired
    ConfessionService confessionService;

    @Autowired
    UserService userService;

    @Autowired
    CommentService commentService;

    @PostMapping("")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Confession> createConfession(@ModelAttribute ConfessionDto confession) {
        return ResponseEntity.ok(confessionService.addConfession(confession, true));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Confession> updateConfession(@PathVariable Long id, @ModelAttribute ConfessionDto confession) {
        Confession updatedConfession = confessionService.updateConfession(id, confession);
        return ResponseEntity.ok(updatedConfession);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteConfession(@PathVariable Long id) {
        confessionService.deleteConfession(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Confession> getConfession(@PathVariable Long id) {
        Confession confession = confessionService.getConfession(id);
        return ResponseEntity.ok(confession);
    }

    @PostMapping("/create-confession-request")
    @PreAuthorize("hasRole('MEMBER, ADMIN')")
    public ResponseEntity<Confession> createConfessionRequest(@ModelAttribute ConfessionDto confession) {
        return new ResponseEntity<>(confessionService.addConfession(confession, false), HttpStatus.OK);
    }

    @PostMapping("/{id}/comment")
    public ResponseEntity<CommentDto> comment(@PathVariable Long id, @RequestBody CommentRequest comment) {
        Long userId = userService.getCurrentUserId();
        return new ResponseEntity<>(new CommentDto(commentService.commentConfession(id, userId, comment)), HttpStatus.OK);
    }

    @PutMapping("/{id}/comment")
    public ResponseEntity<?> updateComment(@PathVariable Long id,
                                           @RequestParam Long commentId,
                                           @RequestBody CommentRequest comment) {
        Long userId = userService.getCurrentUserId();
        Comment comment1 = commentService.updateComment(commentId, userId, comment);
        if (comment1 != null) {
            return new ResponseEntity<>(new CommentDto(comment1), HttpStatus.OK);
        }
        return new ResponseEntity<>("Comment not found or you don't have permission to update this comment", HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}/comment")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id,
                                              @RequestParam Long commentId) {
        commentService.deleteComment(commentId);
        return ResponseEntity.ok().build();
    }
}
