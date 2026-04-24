package com.centros_sass.app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.centros_sass.app.dto.useraddress.UserAddressRequestDTO;
import com.centros_sass.app.dto.useraddress.UserAddressResponseDTO;
import com.centros_sass.app.dto.useraddress.UserAddressUpdateDTO;

public interface UserAddressService {

    Page<UserAddressResponseDTO> findAll(Pageable pageable);

    Page<UserAddressResponseDTO> findAllInactive(Pageable pageable);

    Page<UserAddressResponseDTO> findAllIncludingInactive(Pageable pageable);

    Optional<UserAddressResponseDTO> findById(Integer id);

    List<UserAddressResponseDTO> findByUserId(Integer userId);

    UserAddressResponseDTO save(UserAddressRequestDTO dto);

    Optional<UserAddressResponseDTO> update(Integer id, UserAddressUpdateDTO dto);

    Optional<UserAddressResponseDTO> delete(Integer id);
}