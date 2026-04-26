package com.centros_sass.app.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.centros_sass.app.dto.belongings.ObjectTypeRequestDTO;
import com.centros_sass.app.dto.belongings.ObjectTypeResponseDTO;
import com.centros_sass.app.dto.belongings.ObjectTypeUpdateDTO;

public interface ObjectTypeService {

    Page<ObjectTypeResponseDTO> findAll(Pageable pageable);

    Page<ObjectTypeResponseDTO> findAllInactive(Pageable pageable);

    Page<ObjectTypeResponseDTO> findAllIncludingInactive(Pageable pageable);

    Optional<ObjectTypeResponseDTO> findById(Integer id);

    ObjectTypeResponseDTO save(ObjectTypeRequestDTO dto);

    Optional<ObjectTypeResponseDTO> update(Integer id, ObjectTypeUpdateDTO dto);

    Optional<ObjectTypeResponseDTO> delete(Integer id);

}
