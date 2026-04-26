package com.centros_sass.app.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.centros_sass.app.dto.catalogs.treatments.StorageConditionRequestDTO;
import com.centros_sass.app.dto.catalogs.treatments.StorageConditionResponseDTO;
import com.centros_sass.app.dto.catalogs.treatments.StorageConditionUpdateDTO;

public interface StorageConditionService {

    Page<StorageConditionResponseDTO> findAll(Pageable pageable);

    Optional<StorageConditionResponseDTO> findById(Integer id);

    StorageConditionResponseDTO save(StorageConditionRequestDTO dto);

    Optional<StorageConditionResponseDTO> update(Integer id, StorageConditionUpdateDTO dto);

    void delete(Integer id);

}
