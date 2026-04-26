package com.centros_sass.app.service;

import java.util.List;
import java.util.Optional;

import com.centros_sass.app.dto.catalogs.organization.RoleRequestDTO;
import com.centros_sass.app.dto.catalogs.organization.RoleResponseDTO;
import com.centros_sass.app.dto.catalogs.organization.RoleUpdateDTO;

public interface RoleService {

    List<RoleResponseDTO> findAll();

    Optional<RoleResponseDTO> findById(Integer id);

    RoleResponseDTO save(RoleRequestDTO dto);

    Optional<RoleResponseDTO> update(Integer id, RoleUpdateDTO dto);

    Optional<RoleResponseDTO> delete(Integer id);

}