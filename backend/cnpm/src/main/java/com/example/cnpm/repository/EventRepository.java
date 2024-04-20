package com.example.cnpm.repository;

import com.example.cnpm.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EventRepository extends JpaRepository<Event,Long> {

    @Query("SELECT e FROM Event e ORDER BY e.createdAt DESC")
    List<Event> findAllDesc();
}