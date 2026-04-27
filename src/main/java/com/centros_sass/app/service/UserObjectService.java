package com.centros_sass.app.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.centros_sass.app.dto.belongings.UserObjectRequestDTO;
import com.centros_sass.app.dto.belongings.UserObjectResponseDTO;
import com.centros_sass.app.dto.belongings.UserObjectUpdateDTO;

public interface UserObjectService {

    Page<UserObjectResponseDTO> findAll(Pageable pageable);

    Page<UserObjectResponseDTO> findAllInactive(Pageable pageable);

    Page<UserObjectResponseDTO> findAllIncludingInactive(Pageable pageable);

    Optional<UserObjectResponseDTO> findById(Integer id);

    Page<UserObjectResponseDTO> findByObjectTypeId(Integer objectTypeId, Pageable pageable);

    Page<UserObjectResponseDTO> findByItemConditionId(Integer itemConditionId, Pageable pageable);

    UserObjectResponseDTO save(UserObjectRequestDTO dto);

    Optional<UserObjectResponseDTO> update(Integer id, UserObjectUpdateDTO dto);

    Optional<UserObjectResponseDTO> delete(Integer id);

}
