package com.example.cnpm.controller.dtos;

import com.example.cnpm.entity.Comment;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CommentDto {
    private Long id;
    private String content;
    private UserDto user;
    private ConfessionDto confession;

    public CommentDto(Comment comment) {
        this.id = comment.getId();
        this.content = comment.getContent();
        this.user = new UserDto(comment.getUser());
        this.confession = new ConfessionDto(comment.getConfession());
    }
}
