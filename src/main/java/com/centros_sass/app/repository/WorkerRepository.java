package com.centros_sass.app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.centros_sass.app.model.profiles.workers.Worker;

public interface WorkerRepository extends JpaRepository<Worker, Integer> {

    Optional<Worker> findByEmail(String email);

    @Query("SELECT w FROM Worker w JOIN FETCH w.roles WHERE w.email = :email")
    Optional<Worker> findByEmailWithRoles(@Param("email") String email);

    boolean existsByEmail(String email);

}
