package com.example.cnpm.repository;

import com.example.cnpm.entity.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SkillRepository extends JpaRepository<Skill,Long> {

    @Query("SELECT s FROM Skill s ORDER BY s.createdAt DESC")
    List<Skill> findAllDesc();
}