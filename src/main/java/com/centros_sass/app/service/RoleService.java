package com.centros_sass.app.service;

import java.util.List;
import java.util.Optional;

import com.centros_sass.app.dto.role.RoleRequestDTO;
import com.centros_sass.app.dto.role.RoleResponseDTO;
import com.centros_sass.app.dto.role.RoleUpdateDTO;

public interface RoleService {

    List<RoleResponseDTO> findAll();

    Optional<RoleResponseDTO> findById(Integer id);

    RoleResponseDTO save(RoleRequestDTO dto);

    Optional<RoleResponseDTO> update(Integer id, RoleUpdateDTO dto);

    Optional<RoleResponseDTO> delete(Integer id);

}