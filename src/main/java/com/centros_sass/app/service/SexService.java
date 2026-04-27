package com.centros_sass.app.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.centros_sass.app.dto.catalogs.people.SexRequestDTO;
import com.centros_sass.app.dto.catalogs.people.SexResponseDTO;
import com.centros_sass.app.dto.catalogs.people.SexUpdateDTO;

public interface SexService {

    Page<SexResponseDTO> findAll(Pageable pageable);

    Optional<SexResponseDTO> findById(Integer id);

    SexResponseDTO save(SexRequestDTO dto);

    Optional<SexResponseDTO> update(Integer id, SexUpdateDTO dto);

    void delete(Integer id);

}
