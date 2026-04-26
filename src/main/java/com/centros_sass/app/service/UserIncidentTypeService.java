package com.centros_sass.app.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.centros_sass.app.dto.catalogs.incidents.UserIncidentTypeRequestDTO;
import com.centros_sass.app.dto.catalogs.incidents.UserIncidentTypeResponseDTO;
import com.centros_sass.app.dto.catalogs.incidents.UserIncidentTypeUpdateDTO;

public interface UserIncidentTypeService {

    Page<UserIncidentTypeResponseDTO> findAll(Pageable pageable);

    Optional<UserIncidentTypeResponseDTO> findById(Integer id);

    UserIncidentTypeResponseDTO save(UserIncidentTypeRequestDTO dto);

    Optional<UserIncidentTypeResponseDTO> update(Integer id, UserIncidentTypeUpdateDTO dto);

    void delete(Integer id);

}
