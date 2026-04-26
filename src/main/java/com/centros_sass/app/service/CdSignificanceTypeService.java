package com.centros_sass.app.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.centros_sass.app.dto.catalogs.incidents.CdSignificanceTypeRequestDTO;
import com.centros_sass.app.dto.catalogs.incidents.CdSignificanceTypeResponseDTO;
import com.centros_sass.app.dto.catalogs.incidents.CdSignificanceTypeUpdateDTO;

public interface CdSignificanceTypeService {

    Page<CdSignificanceTypeResponseDTO> findAll(Pageable pageable);

    Optional<CdSignificanceTypeResponseDTO> findById(Integer id);

    CdSignificanceTypeResponseDTO save(CdSignificanceTypeRequestDTO dto);

    Optional<CdSignificanceTypeResponseDTO> update(Integer id, CdSignificanceTypeUpdateDTO dto);

    void delete(Integer id);

}
