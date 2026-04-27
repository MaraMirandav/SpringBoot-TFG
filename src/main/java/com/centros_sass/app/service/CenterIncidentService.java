package com.centros_sass.app.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.centros_sass.app.dto.incidents.CenterIncidentRequestDTO;
import com.centros_sass.app.dto.incidents.CenterIncidentResponseDTO;
import com.centros_sass.app.dto.incidents.CenterIncidentUpdateDTO;

public interface CenterIncidentService {

    Page<CenterIncidentResponseDTO> findAll(Pageable pageable);

    Page<CenterIncidentResponseDTO> findAllInactive(Pageable pageable);

    Page<CenterIncidentResponseDTO> findAllIncludingInactive(Pageable pageable);

    Optional<CenterIncidentResponseDTO> findById(Integer id);

    Page<CenterIncidentResponseDTO> findByReportedById(Integer workerId, Pageable pageable);

    Page<CenterIncidentResponseDTO> findByIncidentStatusId(Integer statusId, Pageable pageable);

    CenterIncidentResponseDTO save(CenterIncidentRequestDTO dto);

    Optional<CenterIncidentResponseDTO> update(Integer id, CenterIncidentUpdateDTO dto);

    Optional<CenterIncidentResponseDTO> delete(Integer id);

}
