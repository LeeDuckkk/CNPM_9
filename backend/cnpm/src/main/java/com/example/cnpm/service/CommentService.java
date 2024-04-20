package com.example.cnpm.service;

import com.example.cnpm.controller.dtos.CommentRequest;
import com.example.cnpm.entity.Comment;

public interface CommentService {

    Comment commentConfession(Long id, Long userId, CommentRequest comment);

    Comment updateComment(Long id, Long userId, CommentRequest comment);

    void deleteComment(Long id);
}
