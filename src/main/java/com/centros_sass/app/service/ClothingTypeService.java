package com.centros_sass.app.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.centros_sass.app.dto.catalogs.belongings.ClothingTypeRequestDTO;
import com.centros_sass.app.dto.catalogs.belongings.ClothingTypeResponseDTO;
import com.centros_sass.app.dto.catalogs.belongings.ClothingTypeUpdateDTO;

public interface ClothingTypeService {

    Page<ClothingTypeResponseDTO> findAll(Pageable pageable);

    Optional<ClothingTypeResponseDTO> findById(Integer id);

    ClothingTypeResponseDTO save(ClothingTypeRequestDTO dto);

    Optional<ClothingTypeResponseDTO> update(Integer id, ClothingTypeUpdateDTO dto);

    void delete(Integer id);

}
