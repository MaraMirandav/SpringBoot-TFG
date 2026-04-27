package com.centros_sass.app.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.centros_sass.app.dto.catalogs.treatments.MedicationApplicationRequestDTO;
import com.centros_sass.app.dto.catalogs.treatments.MedicationApplicationResponseDTO;
import com.centros_sass.app.dto.catalogs.treatments.MedicationApplicationUpdateDTO;

public interface MedicationApplicationService {

    Page<MedicationApplicationResponseDTO> findAll(Pageable pageable);

    Optional<MedicationApplicationResponseDTO> findById(Integer id);

    MedicationApplicationResponseDTO save(MedicationApplicationRequestDTO dto);

    Optional<MedicationApplicationResponseDTO> update(Integer id, MedicationApplicationUpdateDTO dto);

    void delete(Integer id);

}
