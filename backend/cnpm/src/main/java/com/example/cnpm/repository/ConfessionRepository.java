package com.example.cnpm.repository;

import com.example.cnpm.entity.Confession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ConfessionRepository extends JpaRepository<Confession, Long> {

    @Query("SELECT c FROM Confession c WHERE c.isApproved = true ORDER BY c.createdAt DESC")
    List<Confession> findAllApprovedConfession();
}
