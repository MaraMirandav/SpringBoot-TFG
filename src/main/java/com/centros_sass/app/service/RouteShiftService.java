package com.centros_sass.app.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.centros_sass.app.dto.catalogs.transport.RouteShiftRequestDTO;
import com.centros_sass.app.dto.catalogs.transport.RouteShiftResponseDTO;
import com.centros_sass.app.dto.catalogs.transport.RouteShiftUpdateDTO;

public interface RouteShiftService {

    Page<RouteShiftResponseDTO> findAll(Pageable pageable);

    Optional<RouteShiftResponseDTO> findById(Integer id);

    RouteShiftResponseDTO save(RouteShiftRequestDTO dto);

    Optional<RouteShiftResponseDTO> update(Integer id, RouteShiftUpdateDTO dto);

    void delete(Integer id);

}
