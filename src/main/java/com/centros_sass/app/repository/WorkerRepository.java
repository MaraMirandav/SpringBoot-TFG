package com.centros_sass.app.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.centros_sass.app.model.profiles.workers.Worker;

public interface WorkerRepository extends JpaRepository<Worker, Integer> {

    Optional<Worker> findByEmail(String email);

    @Query("SELECT w FROM Worker w LEFT JOIN FETCH w.roles WHERE w.email = :email")
    Optional<Worker> findByEmailWithRoles(@Param("email") String email);

    boolean existsByEmail(String email);

    // DNI
    Optional<Worker> findByDni(String dni);

    boolean existsByDni(String dni);

    // Estado activo/inactivo
    Page<Worker> findAllByIsActiveTrue(Pageable pageable);

    @Query("""
        SELECT w FROM Worker w
        WHERE w.isActive = true
        AND (
            LOWER(w.firstName) LIKE %:search% OR
            LOWER(w.firstSurname) LIKE %:search% OR
            LOWER(w.secondName) LIKE %:search% OR
            LOWER(w.secondSurname) LIKE %:search% OR
            LOWER(w.dni) LIKE %:search% OR
            LOWER(w.email) LIKE %:search%
        )
    """)
    Page<Worker> findAllBySearchAndIsActiveTrue(Pageable pageable, @Param("search") String search);

    Page<Worker> findAllByIsActiveFalse(Pageable pageable);

    @Query("""
        SELECT w FROM Worker w
        WHERE w.isActive = false
        AND (
            LOWER(w.firstName) LIKE %:search% OR
            LOWER(w.firstSurname) LIKE %:search% OR
            LOWER(w.secondName) LIKE %:search% OR
            LOWER(w.secondSurname) LIKE %:search% OR
            LOWER(w.dni) LIKE %:search% OR
            LOWER(w.email) LIKE %:search%
        )
    """)
    Page<Worker> findAllBySearchAndIsActiveFalse(Pageable pageable, @Param("search") String search );
}