package com.centros_sass.app.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.centros_sass.app.dto.catalogs.belongings.ObjectTypeRequestDTO;
import com.centros_sass.app.dto.catalogs.belongings.ObjectTypeResponseDTO;
import com.centros_sass.app.dto.catalogs.belongings.ObjectTypeUpdateDTO;

public interface ObjectTypeService {

    Page<ObjectTypeResponseDTO> findAll(Pageable pageable);

    Optional<ObjectTypeResponseDTO> findById(Integer id);

    ObjectTypeResponseDTO save(ObjectTypeRequestDTO dto);

    Optional<ObjectTypeResponseDTO> update(Integer id, ObjectTypeUpdateDTO dto);

    void delete(Integer id);

}
