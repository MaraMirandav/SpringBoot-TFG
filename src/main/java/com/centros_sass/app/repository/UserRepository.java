package com.centros_sass.app.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.centros_sass.app.model.profiles.users.User;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByDni(String dni);

    boolean existsByDni(String dni);

    Page<User> findAllByIsActiveTrue(Pageable pageable);

    Page<User> findAllByIsActiveFalse(Pageable pageable);

}
