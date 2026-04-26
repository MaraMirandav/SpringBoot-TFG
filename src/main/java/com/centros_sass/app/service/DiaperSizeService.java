package com.centros_sass.app.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.centros_sass.app.dto.catalogs.belongings.DiaperSizeRequestDTO;
import com.centros_sass.app.dto.catalogs.belongings.DiaperSizeResponseDTO;
import com.centros_sass.app.dto.catalogs.belongings.DiaperSizeUpdateDTO;

public interface DiaperSizeService {

    Page<DiaperSizeResponseDTO> findAll(Pageable pageable);

    Optional<DiaperSizeResponseDTO> findById(Integer id);

    DiaperSizeResponseDTO save(DiaperSizeRequestDTO dto);

    Optional<DiaperSizeResponseDTO> update(Integer id, DiaperSizeUpdateDTO dto);

    void delete(Integer id);

}
