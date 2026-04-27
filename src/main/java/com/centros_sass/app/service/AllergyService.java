package com.centros_sass.app.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.centros_sass.app.dto.catalogs.treatments.AllergyRequestDTO;
import com.centros_sass.app.dto.catalogs.treatments.AllergyResponseDTO;
import com.centros_sass.app.dto.catalogs.treatments.AllergyUpdateDTO;

public interface AllergyService {

    Page<AllergyResponseDTO> findAll(Pageable pageable);

    Optional<AllergyResponseDTO> findById(Integer id);

    AllergyResponseDTO save(AllergyRequestDTO dto);

    Optional<AllergyResponseDTO> update(Integer id, AllergyUpdateDTO dto);

    void delete(Integer id);

}
