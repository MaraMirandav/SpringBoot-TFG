package com.centros_sass.app.repository.treatments;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.centros_sass.app.model.treatments.UserMedicalInfo;

@Repository
public interface UserMedicalInfoRepository extends JpaRepository<UserMedicalInfo, Integer> {

    Page<UserMedicalInfo> findAllByIsActiveTrue(Pageable pageable);

    Page<UserMedicalInfo> findAllByIsActiveFalse(Pageable pageable);

    Optional<UserMedicalInfo> findByUserIdAndIsActiveTrue(Integer userId);

    boolean existsByUserIdAndIsActiveTrue(Integer userId);

}
