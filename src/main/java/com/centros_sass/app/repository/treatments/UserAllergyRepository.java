package com.centros_sass.app.repository.treatments;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.centros_sass.app.model.treatments.UserAllergy;

@Repository
public interface UserAllergyRepository extends JpaRepository<UserAllergy, Integer> {

    Page<UserAllergy> findAllByIsActiveTrue(Pageable pageable);

    Page<UserAllergy> findAllByIsActiveFalse(Pageable pageable);

    Page<UserAllergy> findByUserMedicalInfoIdAndIsActiveTrue(Integer infoId, Pageable pageable);

}
