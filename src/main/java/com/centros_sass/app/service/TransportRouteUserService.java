package com.centros_sass.app.service;

import java.util.List;
import java.util.Optional;

import com.centros_sass.app.dto.transport.TransportRouteUserRequestDTO;
import com.centros_sass.app.dto.transport.TransportRouteUserResponseDTO;
import com.centros_sass.app.dto.transport.TransportRouteUserUpdateDTO;

public interface TransportRouteUserService {

    List<TransportRouteUserResponseDTO> findAllByRoute(Integer routeId);

    Optional<TransportRouteUserResponseDTO> findByRouteIdAndUserId(Integer routeId, Integer userId);

    TransportRouteUserResponseDTO save(Integer routeId, TransportRouteUserRequestDTO dto);

    Optional<TransportRouteUserResponseDTO> update(Integer routeId, Integer userId, TransportRouteUserUpdateDTO dto);

    void delete(Integer routeId, Integer userId);

}
