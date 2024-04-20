package com.example.cnpm.service;

import com.example.cnpm.controller.dtos.CommentRequest;
import com.example.cnpm.controller.dtos.ConfessionDto;
import com.example.cnpm.entity.Comment;
import com.example.cnpm.entity.Confession;
import com.example.cnpm.entity.User;
import org.springframework.data.domain.Page;

public interface ConfessionService {
    Confession addConfession(ConfessionDto confession, boolean isApproved);

    Confession updateConfession(Long id, ConfessionDto confession);

    void deleteConfession(Long id);

    Confession getConfession(Long id);

    Confession acceptConfession(Long id);

    Page<ConfessionDto> getAllConfession(int page);
}
