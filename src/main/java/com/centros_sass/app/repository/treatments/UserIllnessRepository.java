package com.centros_sass.app.repository.treatments;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.centros_sass.app.model.treatments.UserIllness;

@Repository
public interface UserIllnessRepository extends JpaRepository<UserIllness, Integer> {

    Page<UserIllness> findAllByIsActiveTrue(Pageable pageable);

    Page<UserIllness> findAllByIsActiveFalse(Pageable pageable);

    Page<UserIllness> findByUserMedicalInfoIdAndIsActiveTrue(Integer infoId, Pageable pageable);

}
