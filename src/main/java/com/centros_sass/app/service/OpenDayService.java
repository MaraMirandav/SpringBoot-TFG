package com.centros_sass.app.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.centros_sass.app.dto.catalogs.calendar.OpenDayRequestDTO;
import com.centros_sass.app.dto.catalogs.calendar.OpenDayResponseDTO;
import com.centros_sass.app.dto.catalogs.calendar.OpenDayUpdateDTO;

public interface OpenDayService {

    Page<OpenDayResponseDTO> findAll(Pageable pageable);

    Optional<OpenDayResponseDTO> findById(Integer id);

    OpenDayResponseDTO save(OpenDayRequestDTO dto);

    Optional<OpenDayResponseDTO> update(Integer id, OpenDayUpdateDTO dto);

    void delete(Integer id);

}
