package com.centros_sass.app.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.centros_sass.app.dto.user.UserRequestDTO;
import com.centros_sass.app.dto.user.UserResponseDTO;
import com.centros_sass.app.dto.user.UserUpdateDTO;

public interface UserService {

    Page<UserResponseDTO> findAll(Pageable pageable);

    Page<UserResponseDTO> findAllIncludingInactive(Pageable pageable);

    Optional<UserResponseDTO> findById(Integer id);

    UserResponseDTO save(UserRequestDTO dto);

    Optional<UserResponseDTO> update(Integer id, UserUpdateDTO dto);

    Optional<UserResponseDTO> delete(Integer id);

}
