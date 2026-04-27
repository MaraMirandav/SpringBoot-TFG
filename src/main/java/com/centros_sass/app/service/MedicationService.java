package com.centros_sass.app.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.centros_sass.app.dto.treatments.MedicationRequestDTO;
import com.centros_sass.app.dto.treatments.MedicationResponseDTO;
import com.centros_sass.app.dto.treatments.MedicationUpdateDTO;

public interface MedicationService {

    Page<MedicationResponseDTO> findAll(Pageable pageable);

    Page<MedicationResponseDTO> findAllInactive(Pageable pageable);

    Page<MedicationResponseDTO> findAllIncludingInactive(Pageable pageable);

    Optional<MedicationResponseDTO> findById(Integer id);

    Page<MedicationResponseDTO> findByUserId(Integer userId, Pageable pageable);

    Page<MedicationResponseDTO> findExpiringSoon(Pageable pageable);

    Page<MedicationResponseDTO> findLowStock(Pageable pageable);

    MedicationResponseDTO save(MedicationRequestDTO dto);

    Optional<MedicationResponseDTO> update(Integer id, MedicationUpdateDTO dto);

    Optional<MedicationResponseDTO> delete(Integer id);

}
