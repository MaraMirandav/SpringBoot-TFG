package com.centros_sass.app.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.centros_sass.app.dto.belongings.UserBelongingRequestDTO;
import com.centros_sass.app.dto.belongings.UserBelongingResponseDTO;
import com.centros_sass.app.dto.belongings.UserBelongingUpdateDTO;

public interface UserBelongingService {

    Page<UserBelongingResponseDTO> findAll(Pageable pageable);

    Page<UserBelongingResponseDTO> findAllInactive(Pageable pageable);

    Page<UserBelongingResponseDTO> findAllIncludingInactive(Pageable pageable);

    Optional<UserBelongingResponseDTO> findById(Integer id);

    Page<UserBelongingResponseDTO> findByUserId(Integer userId, Pageable pageable);

    Page<UserBelongingResponseDTO> findByWorkerId(Integer workerId, Pageable pageable);

    UserBelongingResponseDTO save(UserBelongingRequestDTO dto);

    Optional<UserBelongingResponseDTO> update(Integer id, UserBelongingUpdateDTO dto);

    Optional<UserBelongingResponseDTO> delete(Integer id);

}
