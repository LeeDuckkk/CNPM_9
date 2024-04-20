package com.example.cnpm.service;

import com.example.cnpm.controller.dtos.ConfessionDto;
import com.example.cnpm.entity.Confession;

public interface ConfessionService {
    Confession addConfession(ConfessionDto confession);

    Confession updateConfession(Long id, ConfessionDto confession);

    void deleteConfession(Long id);

    Confession getConfession(Long id);
}
