package com.centros_sass.app.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.centros_sass.app.dto.catalogs.incidents.UserSignificanceTypeRequestDTO;
import com.centros_sass.app.dto.catalogs.incidents.UserSignificanceTypeResponseDTO;
import com.centros_sass.app.dto.catalogs.incidents.UserSignificanceTypeUpdateDTO;

public interface UserSignificanceTypeService {

    Page<UserSignificanceTypeResponseDTO> findAll(Pageable pageable);

    Optional<UserSignificanceTypeResponseDTO> findById(Integer id);

    UserSignificanceTypeResponseDTO save(UserSignificanceTypeRequestDTO dto);

    Optional<UserSignificanceTypeResponseDTO> update(Integer id, UserSignificanceTypeUpdateDTO dto);

    void delete(Integer id);

}
