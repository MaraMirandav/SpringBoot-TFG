package com.centros_sass.app.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.centros_sass.app.dto.bathroom.BathroomTaskRequestDTO;
import com.centros_sass.app.dto.bathroom.BathroomTaskResponseDTO;
import com.centros_sass.app.dto.bathroom.BathroomTaskUpdateDTO;

public interface BathroomTaskService {

    Page<BathroomTaskResponseDTO> findAll(Pageable pageable);

    Page<BathroomTaskResponseDTO> findAllInactive(Pageable pageable);

    Page<BathroomTaskResponseDTO> findAllIncludingInactive(Pageable pageable);

    Optional<BathroomTaskResponseDTO> findById(Integer id);

    BathroomTaskResponseDTO save(BathroomTaskRequestDTO dto);

    Optional<BathroomTaskResponseDTO> update(Integer id, BathroomTaskUpdateDTO dto);

    Optional<BathroomTaskResponseDTO> delete(Integer id);

}
