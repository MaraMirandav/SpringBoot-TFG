package com.centros_sass.app.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.centros_sass.app.dto.catalogs.address.ProvinceRequestDTO;
import com.centros_sass.app.dto.catalogs.address.ProvinceResponseDTO;
import com.centros_sass.app.dto.catalogs.address.ProvinceUpdateDTO;

public interface ProvinceService {

    Page<ProvinceResponseDTO> findAll(Pageable pageable);

    Optional<ProvinceResponseDTO> findById(Integer id);

    ProvinceResponseDTO save(ProvinceRequestDTO dto);

    Optional<ProvinceResponseDTO> update(Integer id, ProvinceUpdateDTO dto);

    void delete(Integer id);

}
