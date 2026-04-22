package com.centros_sass.app.service.impl;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.centros_sass.app.dto.vehicle.RouteVehicleRequestDTO;
import com.centros_sass.app.dto.vehicle.RouteVehicleResponseDTO;
import com.centros_sass.app.dto.vehicle.RouteVehicleUpdateDTO;
import com.centros_sass.app.exception.BadRequestException;
import com.centros_sass.app.exception.ResourceNotFoundException;
import com.centros_sass.app.mapper.RouteVehicleMapper;
import com.centros_sass.app.model.transport.TransportRoute;
import com.centros_sass.app.model.transport.RouteVehicle;
import com.centros_sass.app.repository.RouteVehicleRepository;
import com.centros_sass.app.repository.TransportRouteRepository;
import com.centros_sass.app.service.RouteVehicleService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RouteVehicleServiceImpl implements RouteVehicleService {

    private final RouteVehicleRepository routeVehicleRepository;
    private final TransportRouteRepository transportRouteRepository;
    private final RouteVehicleMapper routeVehicleMapper;

    @Override
    @Transactional(readOnly = true)
    public Page<RouteVehicleResponseDTO> findAll(Pageable pageable) {
        return routeVehicleRepository.findAllByIsActiveTrue(pageable)
                .map(routeVehicleMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<RouteVehicleResponseDTO> findAllInactive(Pageable pageable) {
        return routeVehicleRepository.findAllByIsActiveFalse(pageable)
                .map(routeVehicleMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<RouteVehicleResponseDTO> findAllIncludingInactive(Pageable pageable) {
        return routeVehicleRepository.findAll(pageable)
                .map(routeVehicleMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<RouteVehicleResponseDTO> findById(Integer id) {
        return routeVehicleRepository.findById(id)
                .map(routeVehicleMapper::toResponse);
    }

    @Override
    @Transactional
    public RouteVehicleResponseDTO save(RouteVehicleRequestDTO dto) {
        if (routeVehicleRepository.existsByLicensePlate(dto.licensePlate().trim().toUpperCase())) {
            throw new BadRequestException("La matrícula ya está registrada");
        }

        RouteVehicle vehicle = routeVehicleMapper.toEntity(dto);
        vehicle.setLicensePlate(dto.licensePlate().trim().toUpperCase());

        if (!Boolean.TRUE.equals(dto.hasWheelchair())) {
            vehicle.setWheelchairCapacity(null);
        }

        RouteVehicle saved = routeVehicleRepository.save(vehicle);
        return routeVehicleMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public Optional<RouteVehicleResponseDTO> update(Integer id, RouteVehicleUpdateDTO dto) {
        return routeVehicleRepository.findById(id).map(existing -> {
            if (dto.getLicensePlate() != null) {
                String normalized = dto.getLicensePlate().trim().toUpperCase();
                if (!normalized.equals(existing.getLicensePlate())
                        && routeVehicleRepository.existsByLicensePlate(normalized)) {
                    throw new BadRequestException("La matrícula ya está registrada");
                }
                existing.setLicensePlate(normalized);
            }

            routeVehicleMapper.updateFromDto(dto, existing);

            if (Boolean.TRUE.equals(dto.getHasWheelchair()) && dto.getWheelchairCapacity() == null) {
                existing.setWheelchairCapacity(null);
            }
            if (Boolean.FALSE.equals(dto.getHasWheelchair())) {
                existing.setWheelchairCapacity(null);
            }

            RouteVehicle saved = routeVehicleRepository.saveAndFlush(existing);
            return routeVehicleMapper.toResponse(saved);
        });
    }

    @Override
    @Transactional
    public Optional<RouteVehicleResponseDTO> delete(Integer id) {
        return routeVehicleRepository.findById(id).map(existing -> {
            boolean hasActiveRoutes = transportRouteRepository.findAll().stream()
                    .anyMatch(r -> r.getRouteVehicle() != null
                            && r.getRouteVehicle().getId().equals(id)
                            && Boolean.TRUE.equals(r.getIsActive()));
            if (hasActiveRoutes) {
                throw new BadRequestException("No se puede eliminar: el vehículo está asignado a una ruta activa");
            }

            existing.setIsActive(false);
            RouteVehicle saved = routeVehicleRepository.saveAndFlush(existing);
            return routeVehicleMapper.toResponse(saved);
        });
    }

}
