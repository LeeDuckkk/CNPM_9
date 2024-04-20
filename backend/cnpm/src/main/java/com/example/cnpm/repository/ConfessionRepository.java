package com.example.cnpm.repository;

import com.example.cnpm.entity.Confession;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConfessionRepository extends JpaRepository<Confession, Long> {
}
