package com.centros_sass.app.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.centros_sass.app.dto.catalogs.address.RegionRequestDTO;
import com.centros_sass.app.dto.catalogs.address.RegionResponseDTO;
import com.centros_sass.app.dto.catalogs.address.RegionUpdateDTO;

public interface RegionService {

    Page<RegionResponseDTO> findAll(Pageable pageable);

    Optional<RegionResponseDTO> findById(Integer id);

    RegionResponseDTO save(RegionRequestDTO dto);

    Optional<RegionResponseDTO> update(Integer id, RegionUpdateDTO dto);

    void delete(Integer id);

}
