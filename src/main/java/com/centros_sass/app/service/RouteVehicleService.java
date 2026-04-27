package com.centros_sass.app.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.centros_sass.app.dto.vehicle.RouteVehicleRequestDTO;
import com.centros_sass.app.dto.vehicle.RouteVehicleResponseDTO;
import com.centros_sass.app.dto.vehicle.RouteVehicleUpdateDTO;

public interface RouteVehicleService {

    Page<RouteVehicleResponseDTO> findAll(Pageable pageable);

    Page<RouteVehicleResponseDTO> findAllInactive(Pageable pageable);

    Page<RouteVehicleResponseDTO> findAllIncludingInactive(Pageable pageable);

    Optional<RouteVehicleResponseDTO> findById(Integer id);

    RouteVehicleResponseDTO save(RouteVehicleRequestDTO dto);

    Optional<RouteVehicleResponseDTO> update(Integer id, RouteVehicleUpdateDTO dto);

    Optional<RouteVehicleResponseDTO> delete(Integer id);

}
