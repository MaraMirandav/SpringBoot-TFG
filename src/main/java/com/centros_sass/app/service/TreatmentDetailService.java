package com.centros_sass.app.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.centros_sass.app.dto.treatments.TreatmentDetailRequestDTO;
import com.centros_sass.app.dto.treatments.TreatmentDetailResponseDTO;
import com.centros_sass.app.dto.treatments.TreatmentDetailUpdateDTO;

public interface TreatmentDetailService {

    Page<TreatmentDetailResponseDTO> findAll(Pageable pageable);

    Page<TreatmentDetailResponseDTO> findAllInactive(Pageable pageable);

    Page<TreatmentDetailResponseDTO> findAllIncludingInactive(Pageable pageable);

    Optional<TreatmentDetailResponseDTO> findById(Integer id);

    Page<TreatmentDetailResponseDTO> findActive(Pageable pageable);

    Page<TreatmentDetailResponseDTO> findByMedicationId(Integer medicationId, Pageable pageable);

    TreatmentDetailResponseDTO save(TreatmentDetailRequestDTO dto);

    Optional<TreatmentDetailResponseDTO> update(Integer id, TreatmentDetailUpdateDTO dto);

    Optional<TreatmentDetailResponseDTO> delete(Integer id);

}
