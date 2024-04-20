package com.example.cnpm.repository;

import com.example.cnpm.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    Boolean existsByUsername(String username);

    Boolean existsByPhoneNumber(String phoneNumber);

    @Query("SELECT u FROM User u ORDER BY u.id DESC")
    List<User> findAllDesc();
}
