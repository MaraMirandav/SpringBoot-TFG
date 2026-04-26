package com.centros_sass.app.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.centros_sass.app.dto.people.DependencyRequestDTO;
import com.centros_sass.app.dto.people.DependencyResponseDTO;
import com.centros_sass.app.dto.people.DependencyUpdateDTO;

public interface DependencyService {

    Page<DependencyResponseDTO> findAll(Pageable pageable);

    Optional<DependencyResponseDTO> findById(Integer id);

    DependencyResponseDTO save(DependencyRequestDTO dto);

    Optional<DependencyResponseDTO> update(Integer id, DependencyUpdateDTO dto);

    void delete(Integer id);

}
