package com.centros_sass.app.service;

import com.centros_sass.app.dto.usercontact.UserContactRequestDTO;
import com.centros_sass.app.dto.usercontact.UserContactResponseDTO;
import com.centros_sass.app.dto.usercontact.UserContactUpdateDTO;

import java.util.List;
import java.util.Optional;

public interface UserContactService {

    List<UserContactResponseDTO> findAll();

    List<UserContactResponseDTO> findAllInactive();

    List<UserContactResponseDTO> findAllIncludingInactive();

    Optional<UserContactResponseDTO> findById(Integer id);

    List<UserContactResponseDTO> findByUserId(Integer userId);

    Optional<UserContactResponseDTO> findMainContactByUserId(Integer userId);

    UserContactResponseDTO save(UserContactRequestDTO dto);

    Optional<UserContactResponseDTO> update(Integer id, UserContactUpdateDTO dto);

    Optional<UserContactResponseDTO> delete(Integer id);
}