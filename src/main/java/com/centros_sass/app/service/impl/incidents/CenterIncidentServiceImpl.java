package com.centros_sass.app.service.impl.incidents;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.centros_sass.app.dto.incidents.CenterIncidentRequestDTO;
import com.centros_sass.app.dto.incidents.CenterIncidentResponseDTO;
import com.centros_sass.app.dto.incidents.CenterIncidentUpdateDTO;
import com.centros_sass.app.exception.BadRequestException;
import com.centros_sass.app.exception.ResourceNotFoundException;
import com.centros_sass.app.mapper.incidents.CenterIncidentMapper;
import com.centros_sass.app.model.catalogs.incidents.IncidentStatus;
import com.centros_sass.app.model.catalogs.incidents.center.CdIncidentType;
import com.centros_sass.app.model.catalogs.incidents.center.CdSignificanceType;
import com.centros_sass.app.model.incidents.center.CenterIncident;
import com.centros_sass.app.model.profiles.workers.Worker;
import com.centros_sass.app.repository.catalogs.incidents.CdIncidentTypeRepository;
import com.centros_sass.app.repository.catalogs.incidents.CdSignificanceTypeRepository;
import com.centros_sass.app.repository.catalogs.incidents.IncidentStatusRepository;
import com.centros_sass.app.repository.incidents.CenterIncidentRepository;
import com.centros_sass.app.repository.profiles.WorkerRepository;
import com.centros_sass.app.security.WorkerSecurity;
import com.centros_sass.app.service.CenterIncidentService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CenterIncidentServiceImpl implements CenterIncidentService {

    private final CenterIncidentRepository centerIncidentRepository;
    private final IncidentStatusRepository incidentStatusRepository;
    private final CdIncidentTypeRepository cdIncidentTypeRepository;
    private final CdSignificanceTypeRepository cdSignificanceTypeRepository;
    private final WorkerRepository workerRepository;
    private final CenterIncidentMapper centerIncidentMapper;

    @Override
    @Transactional(readOnly = true)
    public Page<CenterIncidentResponseDTO> findAll(Pageable pageable) {
        return centerIncidentRepository.findAllByIsActiveTrue(pageable)
                .map(centerIncidentMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CenterIncidentResponseDTO> findAllInactive(Pageable pageable) {
        return centerIncidentRepository.findAllByIsActiveFalse(pageable)
                .map(centerIncidentMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CenterIncidentResponseDTO> findAllIncludingInactive(Pageable pageable) {
        return centerIncidentRepository.findAll(pageable)
                .map(centerIncidentMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CenterIncidentResponseDTO> findById(Integer id) {
        return centerIncidentRepository.findById(id)
                .map(centerIncidentMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CenterIncidentResponseDTO> findByReportedById(Integer workerId, Pageable pageable) {
        return centerIncidentRepository.findByReportedByIdAndIsActiveTrue(workerId, pageable)
                .map(centerIncidentMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CenterIncidentResponseDTO> findByIncidentStatusId(Integer statusId, Pageable pageable) {
        return centerIncidentRepository.findByIncidentStatusIdAndIsActiveTrue(statusId, pageable)
                .map(centerIncidentMapper::toResponse);
    }

    @Override
    @Transactional
    public CenterIncidentResponseDTO save(CenterIncidentRequestDTO dto) {
        CenterIncident incident = centerIncidentMapper.toEntity(dto);

        Worker reportedBy = workerRepository.findById(dto.reportedById())
                .orElseThrow(() -> new ResourceNotFoundException("Worker", "id", dto.reportedById()));
        incident.setReportedBy(reportedBy);

        IncidentStatus status;
        if (dto.incidentStatusId() != null) {
            status = incidentStatusRepository.findById(dto.incidentStatusId())
                    .orElseThrow(() -> new ResourceNotFoundException("IncidentStatus", "id", dto.incidentStatusId()));
        } else {
            status = incidentStatusRepository.findByStatusName("Activa")
                    .orElseThrow(() -> new BadRequestException("No se encontró el estado 'Activa' en el sistema"));
        }
        incident.setIncidentStatus(status);

        CdIncidentType type = cdIncidentTypeRepository.findById(dto.cdIncidentTypeId())
                .orElseThrow(() -> new ResourceNotFoundException("CdIncidentType", "id", dto.cdIncidentTypeId()));
        incident.setCdIncident(type);

        CdSignificanceType significance = cdSignificanceTypeRepository.findById(dto.cdSignificanceTypeId())
                .orElseThrow(() -> new ResourceNotFoundException("CdSignificanceType", "id", dto.cdSignificanceTypeId()));
        incident.setCdSignificance(significance);

        incident.setIsActive(true);

        CenterIncident saved = centerIncidentRepository.save(incident);
        return centerIncidentMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public Optional<CenterIncidentResponseDTO> update(Integer id, CenterIncidentUpdateDTO dto) {
        return centerIncidentRepository.findById(id).map(existing -> {
            centerIncidentMapper.updateFromDto(dto, existing);

            if (dto.getReportedById() != null) {
                Worker reportedBy = workerRepository.findById(dto.getReportedById())
                        .orElseThrow(() -> new ResourceNotFoundException("Worker", "id", dto.getReportedById()));
                existing.setReportedBy(reportedBy);
            }

            if (dto.getIncidentStatusId() != null) {
                IncidentStatus newStatus = incidentStatusRepository.findById(dto.getIncidentStatusId())
                        .orElseThrow(() -> new ResourceNotFoundException("IncidentStatus", "id", dto.getIncidentStatusId()));

                validateStatusTransition(existing.getIncidentStatus(), newStatus);
                existing.setIncidentStatus(newStatus);
            }

            if (dto.getCdIncidentTypeId() != null) {
                CdIncidentType type = cdIncidentTypeRepository.findById(dto.getCdIncidentTypeId())
                        .orElseThrow(() -> new ResourceNotFoundException("CdIncidentType", "id", dto.getCdIncidentTypeId()));
                existing.setCdIncident(type);
            }

            if (dto.getCdSignificanceTypeId() != null) {
                CdSignificanceType significance = cdSignificanceTypeRepository.findById(dto.getCdSignificanceTypeId())
                        .orElseThrow(() -> new ResourceNotFoundException("CdSignificanceType", "id", dto.getCdSignificanceTypeId()));
                existing.setCdSignificance(significance);
            }

            CenterIncident saved = centerIncidentRepository.saveAndFlush(existing);
            return centerIncidentMapper.toResponse(saved);
        });
    }

    @Override
    @Transactional
    public Optional<CenterIncidentResponseDTO> delete(Integer id) {
        return centerIncidentRepository.findById(id).map(existing -> {
            existing.setIsActive(false);
            CenterIncident saved = centerIncidentRepository.saveAndFlush(existing);
            return centerIncidentMapper.toResponse(saved);
        });
    }

    private void validateStatusTransition(IncidentStatus currentStatus, IncidentStatus newStatus) {
        String currentName = currentStatus.getStatusName();
        String newName = newStatus.getStatusName();

        if (newName.equals("Cerrada") || newName.equals("Cancelada")) {
            if (!canCloseOrCancelIncident()) {
                throw new BadRequestException("Solo un administrador o director puede cerrar o cancelar una incidencia");
            }
        }

        if (currentName.equals("Activa") && !newName.equals("En revisión") && !newName.equals("Cerrada") && !newName.equals("Cancelada")) {
            throw new BadRequestException("Transición de estado inválida: 'Activa' solo puede cambiar a 'En revisión', 'Cerrada' o 'Cancelada'");
        }

        if (currentName.equals("En revisión") && !newName.equals("Cerrada") && !newName.equals("Cancelada")) {
            throw new BadRequestException("Transición de estado inválida: 'En revisión' solo puede cambiar a 'Cerrada' o 'Cancelada'");
        }

        if (currentName.equals("Cerrada") || currentName.equals("Cancelada")) {
            throw new BadRequestException("No se puede cambiar el estado de una incidencia 'Cerrada' o 'Cancelada'");
        }
    }

    private boolean canCloseOrCancelIncident() {
        Worker currentWorker = getCurrentWorker();
        return currentWorker.getRoles().stream()
                .anyMatch(role -> "ROLE_ADMIN".equals(role.getRoleName())
                        || "ROLE_DIRECTOR".equals(role.getRoleName()));
    }

    private Worker getCurrentWorker() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof WorkerSecurity workerSecurity) {
            return workerSecurity.getWorker();
        }
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return workerRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Worker", "email", email));
    }

}
