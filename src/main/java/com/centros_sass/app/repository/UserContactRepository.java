package com.centros_sass.app.repository;

import com.centros_sass.app.model.profiles.users.UserContact;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserContactRepository extends JpaRepository<UserContact, Integer> {

    List<UserContact> findByUserIdAndIsActiveTrue(Integer userId);

    List<UserContact> findByUserIdAndIsActiveTrueAndIsContactMainTrue(Integer userId);

    Page<UserContact> findByIsActiveTrue(Pageable pageable);

    Page<UserContact> findAll(Pageable pageable);

    Page<UserContact> findByIsActiveFalse(Pageable pageable);
}