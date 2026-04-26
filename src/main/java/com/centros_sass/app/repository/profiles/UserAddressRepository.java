package com.centros_sass.app.repository.profiles;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.centros_sass.app.model.profiles.users.UserAddress;

@Repository
public interface UserAddressRepository extends JpaRepository<UserAddress, Integer> {

    Page<UserAddress> findByIsActiveTrue(Pageable pageable);

    Page<UserAddress> findByIsActiveFalse(Pageable pageable);

    Page<UserAddress> findAll(Pageable pageable);

    List<UserAddress> findByUserIdAndIsActiveTrue(Integer userId);

    List<UserAddress> findByUserId(Integer userId);
}