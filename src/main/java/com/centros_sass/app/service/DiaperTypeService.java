package com.centros_sass.app.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.centros_sass.app.dto.belongings.DiaperTypeRequestDTO;
import com.centros_sass.app.dto.belongings.DiaperTypeResponseDTO;
import com.centros_sass.app.dto.belongings.DiaperTypeUpdateDTO;

public interface DiaperTypeService {

    Page<DiaperTypeResponseDTO> findAll(Pageable pageable);

    Page<DiaperTypeResponseDTO> findAllInactive(Pageable pageable);

    Page<DiaperTypeResponseDTO> findAllIncludingInactive(Pageable pageable);

    Optional<DiaperTypeResponseDTO> findById(Integer id);

    DiaperTypeResponseDTO save(DiaperTypeRequestDTO dto);

    Optional<DiaperTypeResponseDTO> update(Integer id, DiaperTypeUpdateDTO dto);

    Optional<DiaperTypeResponseDTO> delete(Integer id);

}
