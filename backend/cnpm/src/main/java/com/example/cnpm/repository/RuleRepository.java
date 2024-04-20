package com.example.cnpm.repository;

import com.example.cnpm.entity.Rule;
import com.example.cnpm.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RuleRepository extends JpaRepository<Rule,Long> {

    @Query("SELECT r FROM Rule r ORDER BY r.createdAt DESC")
    List<Rule> findAllDesc();
}
