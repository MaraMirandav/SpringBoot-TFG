package com.centros_sass.app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.centros_sass.app.dto.transport.TransportRouteRequestDTO;
import com.centros_sass.app.dto.transport.TransportRouteResponseDTO;
import com.centros_sass.app.dto.transport.TransportRouteUpdateDTO;

public interface TransportRouteService {

    Page<TransportRouteResponseDTO> findAll(Pageable pageable);

    Page<TransportRouteResponseDTO> findAllInactive(Pageable pageable);

    Page<TransportRouteResponseDTO> findAllIncludingInactive(Pageable pageable);

    Optional<TransportRouteResponseDTO> findById(Integer id);

    Page<TransportRouteResponseDTO> findByShift(Integer routeShiftId, Pageable pageable);

    List<TransportRouteResponseDTO> findByDriver(Integer driverId);

    List<TransportRouteResponseDTO> findByCopilot(Integer copilotId);

    TransportRouteResponseDTO save(TransportRouteRequestDTO dto);

    Optional<TransportRouteResponseDTO> update(Integer id, TransportRouteUpdateDTO dto);

    Optional<TransportRouteResponseDTO> delete(Integer id);

}
