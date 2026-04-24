package com.centros_sass.app.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.centros_sass.app.dto.bathroom.BathroomTurnRequestDTO;
import com.centros_sass.app.dto.bathroom.BathroomTurnResponseDTO;
import com.centros_sass.app.dto.bathroom.BathroomTurnUpdateDTO;

public interface BathroomTurnService {

    Page<BathroomTurnResponseDTO> findAll(Pageable pageable);

    Optional<BathroomTurnResponseDTO> findById(Integer id);

    BathroomTurnResponseDTO save(BathroomTurnRequestDTO dto);

    Optional<BathroomTurnResponseDTO> update(Integer id, BathroomTurnUpdateDTO dto);

    Optional<BathroomTurnResponseDTO> delete(Integer id);

}
