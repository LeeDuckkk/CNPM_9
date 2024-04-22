package com.example.cnpm.service;

import com.example.cnpm.controller.dtos.CommentDto;
import com.example.cnpm.controller.dtos.ConfessionDto;
import com.example.cnpm.entity.Confession;
import org.springframework.data.domain.Page;

public interface ConfessionService {
    Confession addConfession(ConfessionDto confession, boolean isApproved);

    Confession updateConfession(Long id, ConfessionDto confession);

    void deleteConfession(Long id);

    Confession getConfession(Long id);

    Page<CommentDto> getComments(Long id, int page);

    Confession acceptConfession(Long id);

    byte[] getConfessionImage(Long id);

    Page<ConfessionDto> getAllConfession(int page);

    Page<ConfessionDto> getAllConfessions(int page);
}
