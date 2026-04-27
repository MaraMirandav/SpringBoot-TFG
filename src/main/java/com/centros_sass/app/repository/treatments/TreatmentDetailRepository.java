package com.centros_sass.app.repository.treatments;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.centros_sass.app.model.treatments.TreatmentDetail;

@Repository
public interface TreatmentDetailRepository extends JpaRepository<TreatmentDetail, Integer> {

    Page<TreatmentDetail> findAllByIsActiveTrue(Pageable pageable);

    Page<TreatmentDetail> findAllByIsActiveFalse(Pageable pageable);

    Page<TreatmentDetail> findByEndDateIsNullAndIsActiveTrue(Pageable pageable);

    Page<TreatmentDetail> findByMedicationsIdAndIsActiveTrue(Integer medicationId, Pageable pageable);

}
