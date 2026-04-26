package com.centros_sass.app.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.centros_sass.app.dto.catalogs.incidents.CdIncidentTypeRequestDTO;
import com.centros_sass.app.dto.catalogs.incidents.CdIncidentTypeResponseDTO;
import com.centros_sass.app.dto.catalogs.incidents.CdIncidentTypeUpdateDTO;

public interface CdIncidentTypeService {

    Page<CdIncidentTypeResponseDTO> findAll(Pageable pageable);

    Optional<CdIncidentTypeResponseDTO> findById(Integer id);

    CdIncidentTypeResponseDTO save(CdIncidentTypeRequestDTO dto);

    Optional<CdIncidentTypeResponseDTO> update(Integer id, CdIncidentTypeUpdateDTO dto);

    void delete(Integer id);

}
