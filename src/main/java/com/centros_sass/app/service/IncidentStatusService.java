package com.centros_sass.app.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.centros_sass.app.dto.catalogs.incidents.IncidentStatusRequestDTO;
import com.centros_sass.app.dto.catalogs.incidents.IncidentStatusResponseDTO;
import com.centros_sass.app.dto.catalogs.incidents.IncidentStatusUpdateDTO;

public interface IncidentStatusService {

    Page<IncidentStatusResponseDTO> findAll(Pageable pageable);

    Optional<IncidentStatusResponseDTO> findById(Integer id);

    IncidentStatusResponseDTO save(IncidentStatusRequestDTO dto);

    Optional<IncidentStatusResponseDTO> update(Integer id, IncidentStatusUpdateDTO dto);

    void delete(Integer id);

}
