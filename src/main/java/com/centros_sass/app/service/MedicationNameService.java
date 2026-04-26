package com.centros_sass.app.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.centros_sass.app.dto.catalogs.treatments.MedicationNameRequestDTO;
import com.centros_sass.app.dto.catalogs.treatments.MedicationNameResponseDTO;
import com.centros_sass.app.dto.catalogs.treatments.MedicationNameUpdateDTO;

public interface MedicationNameService {

    Page<MedicationNameResponseDTO> findAll(Pageable pageable);

    Optional<MedicationNameResponseDTO> findById(Integer id);

    MedicationNameResponseDTO save(MedicationNameRequestDTO dto);

    Optional<MedicationNameResponseDTO> update(Integer id, MedicationNameUpdateDTO dto);

    void delete(Integer id);

}
