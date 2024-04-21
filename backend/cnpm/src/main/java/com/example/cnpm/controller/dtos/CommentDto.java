package com.example.cnpm.controller.dtos;

import com.example.cnpm.entity.Comment;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class CommentDto {
    private Long id;
    private String content;
    private UserDto user;
    private ConfessionDto confession;
    private Date createdDate;

    public CommentDto(Comment comment) {
        this.id = comment.getId();
        this.content = comment.getContent();
        this.user = new UserDto(comment.getUser());
        this.confession = new ConfessionDto(comment.getConfession());
        this.createdDate = comment.getCreatedDate();
    }
}
