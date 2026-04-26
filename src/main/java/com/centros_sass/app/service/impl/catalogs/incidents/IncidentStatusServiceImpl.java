/*
package com.centros_sass.app.service.impl.catalogs.incidents;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.centros_sass.app.dto.catalogs.incidents.IncidentStatusRequestDTO;
import com.centros_sass.app.dto.catalogs.incidents.IncidentStatusResponseDTO;
import com.centros_sass.app.dto.catalogs.incidents.IncidentStatusUpdateDTO;
import com.centros_sass.app.exception.BadRequestException;
import com.centros_sass.app.exception.ResourceNotFoundException;
import com.centros_sass.app.mapper.catalogs.incidents.IncidentStatusMapper;
import com.centros_sass.app.model.catalogs.incidents.IncidentStatus;
import com.centros_sass.app.repository.catalogs.incidents.IncidentStatusRepository;
import com.centros_sass.app.service.IncidentStatusService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IncidentStatusServiceImpl implements IncidentStatusService {

    private final IncidentStatusRepository incidentStatusRepository;
    private final IncidentStatusMapper incidentStatusMapper;

    @Override
    @Transactional(readOnly = true)
    public Page<IncidentStatusResponseDTO> findAll(Pageable pageable) {
        return incidentStatusRepository.findAll(pageable)
                .map(incidentStatusMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<IncidentStatusResponseDTO> findById(Integer id) {
        return incidentStatusRepository.findById(id)
                .map(incidentStatusMapper::toResponse);
    }

    @Override
    @Transactional
    public IncidentStatusResponseDTO save(IncidentStatusRequestDTO dto) {
        if (incidentStatusRepository.existsByStatusName(dto.statusName())) {
            throw new BadRequestException("Ya existe un estado de incidencia con el nombre: " + dto.statusName());
        }

        IncidentStatus entity = incidentStatusMapper.toEntity(dto);
        IncidentStatus saved = incidentStatusRepository.save(entity);
        return incidentStatusMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public Optional<IncidentStatusResponseDTO> update(Integer id, IncidentStatusUpdateDTO dto) {
        return incidentStatusRepository.findById(id).map(existing -> {
            if (dto.getStatusName() != null && !dto.getStatusName().isBlank()) {
                boolean duplicado = incidentStatusRepository.existsByStatusNameAndIdNot(dto.getStatusName(), id);
                if (duplicado) {
                    throw new BadRequestException("Ya existe un estado de incidencia con el nombre: " + dto.getStatusName());
                }
            }

            incidentStatusMapper.updateFromDto(dto, existing);
            IncidentStatus saved = incidentStatusRepository.saveAndFlush(existing);
            return incidentStatusMapper.toResponse(saved);
        });
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        IncidentStatus entity = incidentStatusRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("IncidentStatus", "id", id));

        if (!entity.getCenterIncidents().isEmpty() || !entity.getUserIncidents().isEmpty()) {
            throw new BadRequestException(
                    "No se puede eliminar el estado de incidencia '" + entity.getStatusName() + "' porque está en uso");
        }

        incidentStatusRepository.delete(entity);
    }

}

*/
