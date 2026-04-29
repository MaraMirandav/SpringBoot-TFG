package com.centros_sass.app.service.impl.incidents;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.centros_sass.app.dto.incidents.UserIncidentRequestDTO;
import com.centros_sass.app.dto.incidents.UserIncidentResponseDTO;
import com.centros_sass.app.dto.incidents.UserIncidentUpdateDTO;
import com.centros_sass.app.exception.BadRequestException;
import com.centros_sass.app.exception.ResourceNotFoundException;
import com.centros_sass.app.mapper.incidents.UserIncidentMapper;
import com.centros_sass.app.model.catalogs.incidents.IncidentStatus;
import com.centros_sass.app.model.catalogs.incidents.user.UserIncidentType;
import com.centros_sass.app.model.catalogs.incidents.user.UserSignificanceType;
import com.centros_sass.app.model.incidents.user.UserIncident;
import com.centros_sass.app.model.profiles.users.User;
import com.centros_sass.app.model.profiles.workers.Worker;
import com.centros_sass.app.repository.catalogs.incidents.IncidentStatusRepository;
import com.centros_sass.app.repository.catalogs.incidents.UserIncidentTypeRepository;
import com.centros_sass.app.repository.catalogs.incidents.UserSignificanceTypeRepository;
import com.centros_sass.app.repository.incidents.UserIncidentRepository;
import com.centros_sass.app.repository.profiles.UserRepository;
import com.centros_sass.app.repository.profiles.WorkerRepository;
import com.centros_sass.core.security.WorkerSecurity;
import com.centros_sass.app.service.UserIncidentService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserIncidentServiceImpl implements UserIncidentService {

    private final UserIncidentRepository userIncidentRepository;
    private final IncidentStatusRepository incidentStatusRepository;
    private final UserIncidentTypeRepository userIncidentTypeRepository;
    private final UserSignificanceTypeRepository userSignificanceTypeRepository;
    private final WorkerRepository workerRepository;
    private final UserRepository userRepository;
    private final UserIncidentMapper userIncidentMapper;

    @Override
    @Transactional(readOnly = true)
    public Page<UserIncidentResponseDTO> findAll(Pageable pageable) {
        return userIncidentRepository.findAllByIsActiveTrue(pageable)
                .map(userIncidentMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserIncidentResponseDTO> findAllInactive(Pageable pageable) {
        return userIncidentRepository.findAllByIsActiveFalse(pageable)
                .map(userIncidentMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserIncidentResponseDTO> findAllIncludingInactive(Pageable pageable) {
        return userIncidentRepository.findAll(pageable)
                .map(userIncidentMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UserIncidentResponseDTO> findById(Integer id) {
        return userIncidentRepository.findById(id)
                .map(userIncidentMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserIncidentResponseDTO> findByUserId(Integer userId, Pageable pageable) {
        return userIncidentRepository.findByUserIdAndIsActiveTrue(userId, pageable)
                .map(userIncidentMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserIncidentResponseDTO> findByReportedById(Integer workerId, Pageable pageable) {
        return userIncidentRepository.findByReportedByIdAndIsActiveTrue(workerId, pageable)
                .map(userIncidentMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserIncidentResponseDTO> findByIncidentStatusId(Integer statusId, Pageable pageable) {
        return userIncidentRepository.findByIncidentStatusIdAndIsActiveTrue(statusId, pageable)
                .map(userIncidentMapper::toResponse);
    }

    @Override
    @Transactional
    public UserIncidentResponseDTO save(UserIncidentRequestDTO dto) {
        UserIncident incident = userIncidentMapper.toEntity(dto);

        User user = userRepository.findById(dto.userId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", dto.userId()));
        incident.setUser(user);

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

        UserIncidentType type = userIncidentTypeRepository.findById(dto.userIncidentTypeId())
                .orElseThrow(() -> new ResourceNotFoundException("UserIncidentType", "id", dto.userIncidentTypeId()));
        incident.setUserIncident(type);

        UserSignificanceType significance = userSignificanceTypeRepository.findById(dto.userSignificanceTypeId())
                .orElseThrow(() -> new ResourceNotFoundException("UserSignificanceType", "id", dto.userSignificanceTypeId()));
        incident.setUserSignificance(significance);

        incident.setIsActive(true);

        UserIncident saved = userIncidentRepository.save(incident);
        return userIncidentMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public Optional<UserIncidentResponseDTO> update(Integer id, UserIncidentUpdateDTO dto) {
        return userIncidentRepository.findById(id).map(existing -> {
            userIncidentMapper.updateFromDto(dto, existing);

            if (dto.getUserId() != null) {
                User user = userRepository.findById(dto.getUserId())
                        .orElseThrow(() -> new ResourceNotFoundException("User", "id", dto.getUserId()));
                existing.setUser(user);
            }

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

            if (dto.getUserIncidentTypeId() != null) {
                UserIncidentType type = userIncidentTypeRepository.findById(dto.getUserIncidentTypeId())
                        .orElseThrow(() -> new ResourceNotFoundException("UserIncidentType", "id", dto.getUserIncidentTypeId()));
                existing.setUserIncident(type);
            }

            if (dto.getUserSignificanceTypeId() != null) {
                UserSignificanceType significance = userSignificanceTypeRepository.findById(dto.getUserSignificanceTypeId())
                        .orElseThrow(() -> new ResourceNotFoundException("UserSignificanceType", "id", dto.getUserSignificanceTypeId()));
                existing.setUserSignificance(significance);
            }

            UserIncident saved = userIncidentRepository.saveAndFlush(existing);
            return userIncidentMapper.toResponse(saved);
        });
    }

    @Override
    @Transactional
    public Optional<UserIncidentResponseDTO> delete(Integer id) {
        return userIncidentRepository.findById(id).map(existing -> {
            existing.setIsActive(false);
            UserIncident saved = userIncidentRepository.saveAndFlush(existing);
            return userIncidentMapper.toResponse(saved);
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
