package com.centros_sass.app.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.centros_sass.app.dto.belongings.UserClothingRequestDTO;
import com.centros_sass.app.dto.belongings.UserClothingResponseDTO;
import com.centros_sass.app.dto.belongings.UserClothingUpdateDTO;

public interface UserClothingService {

    Page<UserClothingResponseDTO> findAll(Pageable pageable);

    Page<UserClothingResponseDTO> findAllInactive(Pageable pageable);

    Page<UserClothingResponseDTO> findAllIncludingInactive(Pageable pageable);

    Optional<UserClothingResponseDTO> findById(Integer id);

    Page<UserClothingResponseDTO> findByClothingTypeId(Integer clothingTypeId, Pageable pageable);

    UserClothingResponseDTO save(UserClothingRequestDTO dto);

    Optional<UserClothingResponseDTO> update(Integer id, UserClothingUpdateDTO dto);

    Optional<UserClothingResponseDTO> delete(Integer id);

}
