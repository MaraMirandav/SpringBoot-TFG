package com.centros_sass.app.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.centros_sass.app.dto.role.RoleRequestDTO;
import com.centros_sass.app.dto.role.RoleResponseDTO;
import com.centros_sass.app.dto.role.RoleUpdateDTO;
import com.centros_sass.app.exception.ResourceNotFoundException;
import com.centros_sass.app.mapper.RoleMapper;
import com.centros_sass.app.model.catalogs.fixed.organization.Role;
import com.centros_sass.app.repository.RoleRepository;
import com.centros_sass.app.service.RoleService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    @Override
    @Transactional(readOnly = true)
    public List<RoleResponseDTO> findAll() {
        return roleRepository.findAllByOrderByRoleNameAsc()
            .stream()
            .map(roleMapper::toResponse)
            .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<RoleResponseDTO> findById(Integer id) {
        return roleRepository.findById(id)
            .map(roleMapper::toResponse);
    }

    @Override
    @Transactional
    public RoleResponseDTO save(RoleRequestDTO dto) {
        if (roleRepository.existsByRoleName(dto.roleName())) {
            throw new IllegalStateException("Ya existe un rol con el nombre: " + dto.roleName());
        }
        Role role = roleMapper.toEntity(dto);
        Role saved = roleRepository.save(role);
        return roleMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public Optional<RoleResponseDTO> update(Integer id, RoleUpdateDTO dto) {
        return roleRepository.findById(id)
            .map(existing -> {
                if (dto.getRoleName() != null && !dto.getRoleName().isBlank()) {
                    boolean duplicado = roleRepository.existsByRoleName(dto.getRoleName())
                        && !existing.getRoleName().equals(dto.getRoleName());
                    if (duplicado) {
                        throw new IllegalStateException("Ya existe un rol con el nombre: " + dto.getRoleName());
                    }
                }
                roleMapper.updateFromDto(dto, existing);
                Role saved = roleRepository.save(existing);
                return roleMapper.toResponse(saved);
            });
    }

    @Override
    @Transactional
    public Optional<RoleResponseDTO> delete(Integer id) {
        return roleRepository.findById(id)
            .map(existing -> {
                roleRepository.delete(existing);
                return roleMapper.toResponse(existing);
            });
    }

}