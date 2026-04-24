package com.centros_sass.app.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.centros_sass.app.dto.bathroom.BathroomScheduleRequestDTO;
import com.centros_sass.app.dto.bathroom.BathroomScheduleResponseDTO;
import com.centros_sass.app.dto.bathroom.BathroomScheduleUpdateDTO;

public interface BathroomScheduleService {

    Page<BathroomScheduleResponseDTO> findAll(Pageable pageable);

    Page<BathroomScheduleResponseDTO> findAllInactive(Pageable pageable);

    Page<BathroomScheduleResponseDTO> findAllIncludingInactive(Pageable pageable);

    Optional<BathroomScheduleResponseDTO> findById(Integer id);

    Page<BathroomScheduleResponseDTO> findByUserId(Integer userId, Pageable pageable);

    Page<BathroomScheduleResponseDTO> findByTurnId(Integer turnId, Pageable pageable);

    Page<BathroomScheduleResponseDTO> findByTaskId(Integer taskId, Pageable pageable);

    BathroomScheduleResponseDTO save(BathroomScheduleRequestDTO dto);

    Optional<BathroomScheduleResponseDTO> update(Integer id, BathroomScheduleUpdateDTO dto);

    Optional<BathroomScheduleResponseDTO> delete(Integer id);

}
