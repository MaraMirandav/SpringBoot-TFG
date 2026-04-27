package com.centros_sass.app.repository.treatments;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.centros_sass.app.model.treatments.Medication;

@Repository
public interface MedicationRepository extends JpaRepository<Medication, Integer> {

    Page<Medication> findAllByIsActiveTrue(Pageable pageable);

    Page<Medication> findAllByIsActiveFalse(Pageable pageable);

    Page<Medication> findByUserIdAndIsActiveTrue(Integer userId, Pageable pageable);

    Page<Medication> findByMedicationNameIdAndIsActiveTrue(Integer medicationNameId, Pageable pageable);

    Page<Medication> findByExpirationDateBeforeAndIsActiveTrue(LocalDate date, Pageable pageable);

    Page<Medication> findByStockLessThanEqualAndIsActiveTrue(Integer stock, Pageable pageable);

}
