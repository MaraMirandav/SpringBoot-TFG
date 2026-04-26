package com.centros_sass.app.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.centros_sass.app.dto.catalogs.treatments.IllnessRequestDTO;
import com.centros_sass.app.dto.catalogs.treatments.IllnessResponseDTO;
import com.centros_sass.app.dto.catalogs.treatments.IllnessUpdateDTO;

public interface IllnessService {

    Page<IllnessResponseDTO> findAll(Pageable pageable);

    Optional<IllnessResponseDTO> findById(Integer id);

    IllnessResponseDTO save(IllnessRequestDTO dto);

    Optional<IllnessResponseDTO> update(Integer id, IllnessUpdateDTO dto);

    void delete(Integer id);

}
