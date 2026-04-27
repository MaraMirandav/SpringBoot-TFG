package com.centros_sass.app.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.centros_sass.app.dto.treatments.UserMedicalInfoRequestDTO;
import com.centros_sass.app.dto.treatments.UserMedicalInfoResponseDTO;
import com.centros_sass.app.dto.treatments.UserMedicalInfoUpdateDTO;

public interface UserMedicalInfoService {

    Page<UserMedicalInfoResponseDTO> findAll(Pageable pageable);

    Page<UserMedicalInfoResponseDTO> findAllInactive(Pageable pageable);

    Page<UserMedicalInfoResponseDTO> findAllIncludingInactive(Pageable pageable);

    Optional<UserMedicalInfoResponseDTO> findById(Integer id);

    Optional<UserMedicalInfoResponseDTO> findByUserId(Integer userId);

    UserMedicalInfoResponseDTO save(UserMedicalInfoRequestDTO dto);

    Optional<UserMedicalInfoResponseDTO> update(Integer id, UserMedicalInfoUpdateDTO dto);

    Optional<UserMedicalInfoResponseDTO> delete(Integer id);

}
