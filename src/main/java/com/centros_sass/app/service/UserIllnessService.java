package com.centros_sass.app.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.centros_sass.app.dto.treatments.UserIllnessRequestDTO;
import com.centros_sass.app.dto.treatments.UserIllnessResponseDTO;
import com.centros_sass.app.dto.treatments.UserIllnessUpdateDTO;

public interface UserIllnessService {

    Page<UserIllnessResponseDTO> findAll(Pageable pageable);

    Page<UserIllnessResponseDTO> findAllInactive(Pageable pageable);

    Page<UserIllnessResponseDTO> findAllIncludingInactive(Pageable pageable);

    Optional<UserIllnessResponseDTO> findById(Integer id);

    Page<UserIllnessResponseDTO> findByUserMedicalInfoId(Integer infoId, Pageable pageable);

    UserIllnessResponseDTO save(UserIllnessRequestDTO dto);

    Optional<UserIllnessResponseDTO> update(Integer id, UserIllnessUpdateDTO dto);

    Optional<UserIllnessResponseDTO> delete(Integer id);

}
