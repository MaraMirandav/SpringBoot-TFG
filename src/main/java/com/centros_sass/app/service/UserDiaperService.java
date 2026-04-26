package com.centros_sass.app.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.centros_sass.app.dto.belongings.UserDiaperRequestDTO;
import com.centros_sass.app.dto.belongings.UserDiaperResponseDTO;
import com.centros_sass.app.dto.belongings.UserDiaperUpdateDTO;

public interface UserDiaperService {

    Page<UserDiaperResponseDTO> findAll(Pageable pageable);

    Page<UserDiaperResponseDTO> findAllInactive(Pageable pageable);

    Page<UserDiaperResponseDTO> findAllIncludingInactive(Pageable pageable);

    Optional<UserDiaperResponseDTO> findById(Integer id);

    Page<UserDiaperResponseDTO> findByDiaperSizeId(Integer diaperSizeId, Pageable pageable);

    Page<UserDiaperResponseDTO> findByDiaperTypeId(Integer diaperTypeId, Pageable pageable);

    UserDiaperResponseDTO save(UserDiaperRequestDTO dto);

    Optional<UserDiaperResponseDTO> update(Integer id, UserDiaperUpdateDTO dto);

    Optional<UserDiaperResponseDTO> delete(Integer id);

}
