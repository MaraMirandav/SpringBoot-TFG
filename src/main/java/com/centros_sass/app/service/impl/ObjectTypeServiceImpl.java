package com.centros_sass.app.service.impl;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.centros_sass.app.dto.belongings.ObjectTypeRequestDTO;
import com.centros_sass.app.dto.belongings.ObjectTypeResponseDTO;
import com.centros_sass.app.dto.belongings.ObjectTypeUpdateDTO;
import com.centros_sass.app.exception.BadRequestException;
import com.centros_sass.app.mapper.ObjectTypeMapper;
import com.centros_sass.app.model.catalogs.dynamic.belongings.ObjectType;
import com.centros_sass.app.repository.ObjectTypeRepository;
import com.centros_sass.app.service.ObjectTypeService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ObjectTypeServiceImpl implements ObjectTypeService {

    private final ObjectTypeRepository objectTypeRepository;
    private final ObjectTypeMapper objectTypeMapper;

    @Override
    @Transactional(readOnly = true)
    public Page<ObjectTypeResponseDTO> findAll(Pageable pageable) {
        return objectTypeRepository.findAllByIsActiveTrue(pageable)
                .map(objectTypeMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ObjectTypeResponseDTO> findAllInactive(Pageable pageable) {
        return objectTypeRepository.findAllByIsActiveFalse(pageable)
                .map(objectTypeMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ObjectTypeResponseDTO> findAllIncludingInactive(Pageable pageable) {
        return objectTypeRepository.findAll(pageable)
                .map(objectTypeMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ObjectTypeResponseDTO> findById(Integer id) {
        return objectTypeRepository.findById(id)
                .map(objectTypeMapper::toResponse);
    }

    @Override
    @Transactional
    public ObjectTypeResponseDTO save(ObjectTypeRequestDTO dto) {
        if (objectTypeRepository.existsByObjectName(dto.objectName())) {
            throw new BadRequestException("Ya existe un tipo de objeto con el nombre: " + dto.objectName());
        }

        ObjectType type = objectTypeMapper.toEntity(dto);
        ObjectType saved = objectTypeRepository.save(type);
        return objectTypeMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public Optional<ObjectTypeResponseDTO> update(Integer id, ObjectTypeUpdateDTO dto) {
        return objectTypeRepository.findById(id).map(existing -> {
            if (dto.getObjectName() != null && !dto.getObjectName().isBlank()) {
                boolean duplicado = objectTypeRepository.existsByObjectNameAndIdNot(dto.getObjectName(), id);
                if (duplicado) {
                    throw new BadRequestException("Ya existe un tipo de objeto con el nombre: " + dto.getObjectName());
                }
            }

            objectTypeMapper.updateFromDto(dto, existing);
            ObjectType saved = objectTypeRepository.saveAndFlush(existing);
            return objectTypeMapper.toResponse(saved);
        });
    }

    @Override
    @Transactional
    public Optional<ObjectTypeResponseDTO> delete(Integer id) {
        return objectTypeRepository.findById(id).map(existing -> {
            existing.setIsActive(false);
            ObjectType saved = objectTypeRepository.saveAndFlush(existing);
            return objectTypeMapper.toResponse(saved);
        });
    }

}
