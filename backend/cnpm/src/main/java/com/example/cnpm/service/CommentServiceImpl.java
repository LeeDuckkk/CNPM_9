package com.example.cnpm.service;

import com.example.cnpm.controller.dtos.CommentRequest;
import com.example.cnpm.entity.Comment;
import com.example.cnpm.entity.Confession;
import com.example.cnpm.entity.User;
import com.example.cnpm.repository.CommentRepository;
import com.example.cnpm.repository.ConfessionRepository;
import com.example.cnpm.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ConfessionRepository confessionRepository;

    @Override
    public Comment commentConfession(Long id, Long userId, CommentRequest comment) {
        Confession confession = confessionRepository.findById(id).orElse(null);
        User user = userRepository.findById(userId).orElse(null);
        if (confession == null || user == null) {
            return null;
        }

        Comment newComment = new Comment();
        newComment.setContent(comment.getContent());
        newComment.setConfession(confession);
        newComment.setUser(user);

        return commentRepository.save(newComment);
    }

    @Override
    public Comment updateComment(Long id, Long userId, CommentRequest comment) {
        Comment oldComment = commentRepository.findById(id).orElse(null);
        if (oldComment == null) {
            return null;
        }
        if (!oldComment.getUser().getId().equals(userId)) {
            return null;
        }

        oldComment.setContent(comment.getContent());
        return commentRepository.save(oldComment);
    }

    @Override
    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }
}
