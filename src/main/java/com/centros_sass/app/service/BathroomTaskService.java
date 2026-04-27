package com.centros_sass.app.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.centros_sass.app.dto.catalogs.bathroom.BathroomTaskRequestDTO;
import com.centros_sass.app.dto.catalogs.bathroom.BathroomTaskResponseDTO;
import com.centros_sass.app.dto.catalogs.bathroom.BathroomTaskUpdateDTO;

public interface BathroomTaskService {

    Page<BathroomTaskResponseDTO> findAll(Pageable pageable);

    Optional<BathroomTaskResponseDTO> findById(Integer id);

    BathroomTaskResponseDTO save(BathroomTaskRequestDTO dto);

    Optional<BathroomTaskResponseDTO> update(Integer id, BathroomTaskUpdateDTO dto);

    void delete(Integer id);

}
