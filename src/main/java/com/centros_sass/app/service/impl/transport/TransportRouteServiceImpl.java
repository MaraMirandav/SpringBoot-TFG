package com.centros_sass.app.service.impl.transport;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.centros_sass.app.dto.transport.TransportRouteRequestDTO;
import com.centros_sass.app.dto.transport.TransportRouteResponseDTO;
import com.centros_sass.app.dto.transport.TransportRouteUpdateDTO;
import com.centros_sass.app.exception.BadRequestException;
import com.centros_sass.app.exception.ResourceNotFoundException;
import com.centros_sass.app.mapper.transport.TransportRouteMapper;
import com.centros_sass.app.model.catalogs.transport.RouteShift;
import com.centros_sass.app.model.profiles.workers.Worker;
import com.centros_sass.app.model.transport.RouteVehicle;
import com.centros_sass.app.model.transport.TransportRoute;
import com.centros_sass.app.repository.catalogs.transport.RouteShiftRepository;
import com.centros_sass.app.repository.transport.RouteVehicleRepository;
import com.centros_sass.app.repository.transport.TransportRouteRepository;
import com.centros_sass.app.repository.profiles.WorkerRepository;
import com.centros_sass.app.service.TransportRouteService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TransportRouteServiceImpl implements TransportRouteService {

    private final TransportRouteRepository transportRouteRepository;
    private final RouteShiftRepository routeShiftRepository;
    private final RouteVehicleRepository routeVehicleRepository;
    private final WorkerRepository workerRepository;
    private final TransportRouteMapper transportRouteMapper;

    @Override
    @Transactional(readOnly = true)
    public Page<TransportRouteResponseDTO> findAll(Pageable pageable) {
        return transportRouteRepository.findAllByIsActiveTrue(pageable)
                .map(transportRouteMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TransportRouteResponseDTO> findAllInactive(Pageable pageable) {
        return transportRouteRepository.findAllByIsActiveFalse(pageable)
                .map(transportRouteMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TransportRouteResponseDTO> findAllIncludingInactive(Pageable pageable) {
        return transportRouteRepository.findAll(pageable)
                .map(transportRouteMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TransportRouteResponseDTO> findById(Integer id) {
        return transportRouteRepository.findById(id)
                .map(transportRouteMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TransportRouteResponseDTO> findByShift(Integer routeShiftId, Pageable pageable) {
        return transportRouteRepository.findByRouteShiftIdAndIsActiveTrue(routeShiftId, pageable)
                .map(transportRouteMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TransportRouteResponseDTO> findByDriver(Integer driverId) {
        return transportRouteRepository.findByDriverId(driverId).stream()
                .map(transportRouteMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<TransportRouteResponseDTO> findByCopilot(Integer copilotId) {
        return transportRouteRepository.findByCopilotId(copilotId).stream()
                .map(transportRouteMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public TransportRouteResponseDTO save(TransportRouteRequestDTO dto) {
        if (dto.startTime().isAfter(dto.endTime()) || dto.startTime().equals(dto.endTime())) {
            throw new BadRequestException("La hora de inicio debe ser anterior a la hora de fin");
        }

        if (transportRouteRepository.existsByRouteNumber(dto.routeNumber())) {
            throw new BadRequestException("El número de ruta ya está registrado");
        }

        if (dto.driverId().equals(dto.copilotId())) {
            throw new BadRequestException("El conductor y el copiloto no pueden ser la misma persona");
        }

        TransportRoute route = transportRouteMapper.toEntity(dto);
        assignRelations(route, dto.routeShiftId(), dto.routeVehicleId(), dto.driverId(), dto.copilotId());

        TransportRoute saved = transportRouteRepository.save(route);
        return transportRouteMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public Optional<TransportRouteResponseDTO> update(Integer id, TransportRouteUpdateDTO dto) {
        return transportRouteRepository.findById(id).map(existing -> {
            if (dto.getStartTime() != null && dto.getEndTime() != null) {
                if (dto.getStartTime().isAfter(dto.getEndTime()) || dto.getStartTime().equals(dto.getEndTime())) {
                    throw new BadRequestException("La hora de inicio debe ser anterior a la hora de fin");
                }
            } else if (dto.getStartTime() != null && dto.getStartTime().isAfter(existing.getEndTime())) {
                throw new BadRequestException("La hora de inicio debe ser anterior a la hora de fin");
            } else if (dto.getEndTime() != null && dto.getEndTime().isBefore(existing.getStartTime())) {
                throw new BadRequestException("La hora de fin debe ser posterior a la hora de inicio");
            }

            if (dto.getRouteNumber() != null) {
                if (!dto.getRouteNumber().equals(existing.getRouteNumber())
                        && transportRouteRepository.existsByRouteNumberAndIdNot(dto.getRouteNumber(), id)) {
                    throw new BadRequestException("El número de ruta ya está registrado");
                }
            }

            if (dto.getDriverId() != null && dto.getCopilotId() != null) {
                if (dto.getDriverId().equals(dto.getCopilotId())) {
                    throw new BadRequestException("El conductor y el copiloto no pueden ser la misma persona");
                }
            }

            transportRouteMapper.updateFromDto(dto, existing);

            if (dto.getRouteShiftId() != null) {
                RouteShift shift = routeShiftRepository.findById(dto.getRouteShiftId())
                        .orElseThrow(() -> new ResourceNotFoundException("RouteShift", "id", dto.getRouteShiftId()));
                existing.setRouteShift(shift);
            }

            if (dto.getRouteVehicleId() != null) {
                RouteVehicle vehicle = routeVehicleRepository.findById(dto.getRouteVehicleId())
                        .orElseThrow(() -> new ResourceNotFoundException("RouteVehicle", "id", dto.getRouteVehicleId()));
                existing.setRouteVehicle(vehicle);
            }

            if (dto.getDriverId() != null) {
                Worker driver = workerRepository.findById(dto.getDriverId())
                        .orElseThrow(() -> new ResourceNotFoundException("Worker", "id", dto.getDriverId()));
                existing.setDriver(driver);
            }

            if (dto.getCopilotId() != null) {
                Worker copilot = workerRepository.findById(dto.getCopilotId())
                        .orElseThrow(() -> new ResourceNotFoundException("Worker", "id", dto.getCopilotId()));
                existing.setCopilot(copilot);
            }

            TransportRoute saved = transportRouteRepository.saveAndFlush(existing);
            return transportRouteMapper.toResponse(saved);
        });
    }

    @Override
    @Transactional
    public Optional<TransportRouteResponseDTO> delete(Integer id) {
        return transportRouteRepository.findById(id).map(existing -> {
            existing.setIsActive(false);
            TransportRoute saved = transportRouteRepository.saveAndFlush(existing);
            return transportRouteMapper.toResponse(saved);
        });
    }

    // Métodos Helpers

    private void assignRelations(TransportRoute route, Integer shiftId, Integer vehicleId, Integer driverId, Integer copilotId) {
        RouteShift shift = routeShiftRepository.findById(shiftId)
                .orElseThrow(() -> new ResourceNotFoundException("RouteShift", "id", shiftId));
        route.setRouteShift(shift);

        RouteVehicle vehicle = routeVehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new ResourceNotFoundException("RouteVehicle", "id", vehicleId));
        route.setRouteVehicle(vehicle);

        Worker driver = workerRepository.findById(driverId)
                .orElseThrow(() -> new ResourceNotFoundException("Worker", "id", driverId));
        route.setDriver(driver);

        Worker copilot = workerRepository.findById(copilotId)
                .orElseThrow(() -> new ResourceNotFoundException("Worker", "id", copilotId));
        route.setCopilot(copilot);
    }

}
