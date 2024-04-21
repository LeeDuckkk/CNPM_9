package com.example.cnpm.repository;

import com.example.cnpm.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("SELECT c FROM Comment c WHERE c.confession.id = ?1 ORDER BY c.createdDate DESC")
    List<Comment> findByConfessionId(Long id);
}
