package com.centros_sass.app.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.centros_sass.app.dto.belongings.ClothingTypeRequestDTO;
import com.centros_sass.app.dto.belongings.ClothingTypeResponseDTO;
import com.centros_sass.app.dto.belongings.ClothingTypeUpdateDTO;

public interface ClothingTypeService {

    Page<ClothingTypeResponseDTO> findAll(Pageable pageable);

    Page<ClothingTypeResponseDTO> findAllInactive(Pageable pageable);

    Page<ClothingTypeResponseDTO> findAllIncludingInactive(Pageable pageable);

    Optional<ClothingTypeResponseDTO> findById(Integer id);

    ClothingTypeResponseDTO save(ClothingTypeRequestDTO dto);

    Optional<ClothingTypeResponseDTO> update(Integer id, ClothingTypeUpdateDTO dto);

    Optional<ClothingTypeResponseDTO> delete(Integer id);

}
