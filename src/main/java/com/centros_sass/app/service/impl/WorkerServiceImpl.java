package com.centros_sass.app.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.centros_sass.app.dto.worker.WorkerRequestDTO;
import com.centros_sass.app.dto.worker.WorkerResponseDTO;
import com.centros_sass.app.dto.worker.WorkerUpdateDTO;
import com.centros_sass.app.exception.BadRequestException;
import com.centros_sass.app.mapper.WorkerMapper;
import com.centros_sass.app.model.catalogs.fixed.organization.Role;
import com.centros_sass.app.model.profiles.workers.Worker;
import com.centros_sass.app.repository.RoleRepository;
import com.centros_sass.app.repository.WorkerRepository;
import com.centros_sass.app.service.WorkerService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WorkerServiceImpl implements WorkerService {

    private final WorkerRepository workerRepository;
    private final RoleRepository roleRepository;
    private final WorkerMapper workerMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public Page<WorkerResponseDTO> findAll(Pageable pageable) {
        return workerRepository.findAllByIsActiveTrue(pageable)
                .map(workerMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<WorkerResponseDTO> findAllIncludingInactive(Pageable pageable) {
        Page<Worker> workers = workerRepository.findAll(pageable);
        return workers.map(workerMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<WorkerResponseDTO> findById(Integer id) {
        return workerRepository.findById(id)
                .map(workerMapper::toResponse);
    }

    @Override
    @Transactional
    public WorkerResponseDTO save(WorkerRequestDTO dto) {
        String normalizedEmail = dto.email().trim().toLowerCase();

        if (workerRepository.existsByEmail(normalizedEmail)) {
            throw new BadRequestException("El email ya está registrado");
        }

        if (dto.dni() != null && workerRepository.existsByDni(dto.dni().trim())) {
            throw new BadRequestException("El DNI ya está registrado");
        }

        Worker worker = workerMapper.toEntity(dto);
        worker.setEmail(normalizedEmail);
        worker.setPassword(passwordEncoder.encode(dto.password()));

        assignRoles(worker, dto.roleIds());

        Worker saved = workerRepository.save(worker);
        return workerMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public Optional<WorkerResponseDTO> update(Integer id, WorkerUpdateDTO dto) {
        return workerRepository.findById(id).map(existing -> {
            if (dto.getEmail() != null) {
                String normalizedEmail = dto.getEmail().trim().toLowerCase();
                if (!normalizedEmail.equals(existing.getEmail())
                        && workerRepository.existsByEmail(normalizedEmail)) {
                    throw new BadRequestException("El email ya está registrado");
                }
                existing.setEmail(normalizedEmail);
            }

            if (dto.getDni() != null) {
                String trimmedDni = dto.getDni().trim();
                if (!trimmedDni.equals(existing.getDni())
                        && workerRepository.existsByDni(trimmedDni)) {
                    throw new BadRequestException("El DNI ya está registrado");
                }
                existing.setDni(trimmedDni);
            }

            if (dto.getPassword() != null) {
                existing.setPassword(passwordEncoder.encode(dto.getPassword()));
            }

            workerMapper.updateFromDto(dto, existing);

            if (dto.getRoleIds() != null) {
                updateRoles(existing, dto.getRoleIds());
            }

            Worker saved = workerRepository.saveAndFlush(existing);
            return workerMapper.toResponse(saved);
        });
    }

    @Override
    @Transactional
    public Optional<WorkerResponseDTO> delete(Integer id) {
        return workerRepository.findById(id).map(existing -> {
            existing.setIsActive(false);
            Worker saved = workerRepository.saveAndFlush(existing);
            return workerMapper.toResponse(saved);
        });
    }

    // --- Helpers ---

    private void assignRoles(Worker worker, Set<Integer> roleIds) {
        if (roleIds == null || roleIds.isEmpty()) {
            Role defaultRole = roleRepository.findByRoleName("ROLE_TAS")
                    .orElseThrow(() -> new IllegalStateException(
                            "El rol ROLE_TAS no existe en la base de datos"));
            worker.addRole(defaultRole);
            return;
        }

        List<Role> roles = roleRepository.findAllById(roleIds);
        if (roles.size() != roleIds.size()) {
            throw new BadRequestException("Uno o más roles no existen");
        }
        roles.forEach(worker::addRole);
    }

    private void updateRoles(Worker worker, Set<Integer> roleIds) {
        worker.getRoles().clear();
        if (roleIds.isEmpty()) {
            Role defaultRole = roleRepository.findByRoleName("ROLE_TAS")
                    .orElseThrow(() -> new IllegalStateException(
                            "El rol ROLE_TAS no existe en la base de datos"));
            worker.addRole(defaultRole);
        } else {
            List<Role> roles = roleRepository.findAllById(roleIds);
            if (roles.size() != roleIds.size()) {
                throw new BadRequestException("Uno o más roles no existen");
            }
            roles.forEach(worker::addRole);
        }
    }

}
