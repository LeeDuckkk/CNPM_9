package com.example.cnpm.repository;

import com.example.cnpm.entity.Rule;
import com.example.cnpm.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RuleRepository extends JpaRepository<Rule,Long> {
}
