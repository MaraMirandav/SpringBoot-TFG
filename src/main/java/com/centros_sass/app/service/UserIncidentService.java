package com.centros_sass.app.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.centros_sass.app.dto.incidents.UserIncidentRequestDTO;
import com.centros_sass.app.dto.incidents.UserIncidentResponseDTO;
import com.centros_sass.app.dto.incidents.UserIncidentUpdateDTO;

public interface UserIncidentService {

    Page<UserIncidentResponseDTO> findAll(Pageable pageable);

    Page<UserIncidentResponseDTO> findAllInactive(Pageable pageable);

    Page<UserIncidentResponseDTO> findAllIncludingInactive(Pageable pageable);

    Optional<UserIncidentResponseDTO> findById(Integer id);

    Page<UserIncidentResponseDTO> findByUserId(Integer userId, Pageable pageable);

    Page<UserIncidentResponseDTO> findByReportedById(Integer workerId, Pageable pageable);

    Page<UserIncidentResponseDTO> findByIncidentStatusId(Integer statusId, Pageable pageable);

    UserIncidentResponseDTO save(UserIncidentRequestDTO dto);

    Optional<UserIncidentResponseDTO> update(Integer id, UserIncidentUpdateDTO dto);

    Optional<UserIncidentResponseDTO> delete(Integer id);

}
