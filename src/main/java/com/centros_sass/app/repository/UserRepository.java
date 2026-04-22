package com.centros_sass.app.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.centros_sass.app.model.profiles.users.User;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByDni(String dni);

    boolean existsByDni(String dni);

    Page<User> findAllByIsActiveTrue(Pageable pageable);

    @Query("""
        SELECT u FROM User u
        WHERE u.isActive = true
        AND (
            LOWER(u.firstName) LIKE %:search% OR
            LOWER(u.firstSurname) LIKE %:search% OR
            LOWER(u.alias) LIKE %:search%
        )
        """)
    Page<User> findAllBySearchAndIsActiveTrue(Pageable pageable, String search);

    Page<User> findAllByIsActiveFalse(Pageable pageable);

    @Query("""
        SELECT u FROM User u
        WHERE u.isActive = false
        AND (
            LOWER(u.firstName) LIKE %:search% OR
            LOWER(u.firstSurname) LIKE %:search% OR
            LOWER(u.alias) LIKE %:search%
        )
        """)
    Page<User> findAllBySearchAndIsActiveFalse(Pageable pageable, String search);
}