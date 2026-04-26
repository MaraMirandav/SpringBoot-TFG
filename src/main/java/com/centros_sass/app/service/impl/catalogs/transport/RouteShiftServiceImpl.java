package com.centros_sass.app.service.impl.catalogs.transport;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.centros_sass.app.dto.catalogs.transport.RouteShiftRequestDTO;
import com.centros_sass.app.dto.catalogs.transport.RouteShiftResponseDTO;
import com.centros_sass.app.dto.catalogs.transport.RouteShiftUpdateDTO;
import com.centros_sass.app.exception.BadRequestException;
import com.centros_sass.app.exception.ResourceNotFoundException;
import com.centros_sass.app.mapper.catalogs.transport.RouteShiftMapper;
import com.centros_sass.app.model.catalogs.transport.RouteShift;
import com.centros_sass.app.repository.catalogs.transport.RouteShiftRepository;
import com.centros_sass.app.service.RouteShiftService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RouteShiftServiceImpl implements RouteShiftService {

    private final RouteShiftRepository routeShiftRepository;
    private final RouteShiftMapper routeShiftMapper;

    @Override
    @Transactional(readOnly = true)
    public Page<RouteShiftResponseDTO> findAll(Pageable pageable) {
        return routeShiftRepository.findAll(pageable)
                .map(routeShiftMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<RouteShiftResponseDTO> findById(Integer id) {
        return routeShiftRepository.findById(id)
                .map(routeShiftMapper::toResponse);
    }

    @Override
    @Transactional
    public RouteShiftResponseDTO save(RouteShiftRequestDTO dto) {
        if (routeShiftRepository.existsByRouteName(dto.routeName())) {
            throw new BadRequestException("Ya existe un turno de ruta con el nombre: " + dto.routeName());
        }

        RouteShift entity = routeShiftMapper.toEntity(dto);
        RouteShift saved = routeShiftRepository.save(entity);
        return routeShiftMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public Optional<RouteShiftResponseDTO> update(Integer id, RouteShiftUpdateDTO dto) {
        return routeShiftRepository.findById(id).map(existing -> {
            if (dto.getRouteName() != null && !dto.getRouteName().isBlank()) {
                boolean duplicado = routeShiftRepository.existsByRouteNameAndIdNot(dto.getRouteName(), id);
                if (duplicado) {
                    throw new BadRequestException("Ya existe un turno de ruta con el nombre: " + dto.getRouteName());
                }
            }

            routeShiftMapper.updateFromDto(dto, existing);
            RouteShift saved = routeShiftRepository.saveAndFlush(existing);
            return routeShiftMapper.toResponse(saved);
        });
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        RouteShift entity = routeShiftRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("RouteShift", "id", id));

        if (!entity.getTransportRoutes().isEmpty()) {
            throw new BadRequestException(
                    "No se puede eliminar el turno de ruta '" + entity.getRouteName() + "' porque está en uso");
        }

        routeShiftRepository.delete(entity);
    }

}
