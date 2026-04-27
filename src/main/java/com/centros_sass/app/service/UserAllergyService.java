package com.centros_sass.app.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.centros_sass.app.dto.treatments.UserAllergyRequestDTO;
import com.centros_sass.app.dto.treatments.UserAllergyResponseDTO;
import com.centros_sass.app.dto.treatments.UserAllergyUpdateDTO;

public interface UserAllergyService {

    Page<UserAllergyResponseDTO> findAll(Pageable pageable);

    Page<UserAllergyResponseDTO> findAllInactive(Pageable pageable);

    Page<UserAllergyResponseDTO> findAllIncludingInactive(Pageable pageable);

    Optional<UserAllergyResponseDTO> findById(Integer id);

    Page<UserAllergyResponseDTO> findByUserMedicalInfoId(Integer infoId, Pageable pageable);

    UserAllergyResponseDTO save(UserAllergyRequestDTO dto);

    Optional<UserAllergyResponseDTO> update(Integer id, UserAllergyUpdateDTO dto);

    Optional<UserAllergyResponseDTO> delete(Integer id);

}
