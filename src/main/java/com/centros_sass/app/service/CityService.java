package com.centros_sass.app.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.centros_sass.app.dto.catalogs.address.CityRequestDTO;
import com.centros_sass.app.dto.catalogs.address.CityResponseDTO;
import com.centros_sass.app.dto.catalogs.address.CityUpdateDTO;

public interface CityService {

    Page<CityResponseDTO> findAll(Pageable pageable);

    Optional<CityResponseDTO> findById(Integer id);

    CityResponseDTO save(CityRequestDTO dto);

    Optional<CityResponseDTO> update(Integer id, CityUpdateDTO dto);

    void delete(Integer id);

}
