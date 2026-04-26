/*
package com.centros_sass.app.service.impl.catalogs.treatments;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.centros_sass.app.dto.catalogs.treatments.StorageConditionRequestDTO;
import com.centros_sass.app.dto.catalogs.treatments.StorageConditionResponseDTO;
import com.centros_sass.app.dto.catalogs.treatments.StorageConditionUpdateDTO;
import com.centros_sass.app.exception.BadRequestException;
import com.centros_sass.app.exception.ResourceNotFoundException;
import com.centros_sass.app.mapper.catalogs.treatments.StorageConditionMapper;
import com.centros_sass.app.model.catalogs.treatments.StorageCondition;
import com.centros_sass.app.repository.catalogs.treatments.StorageConditionRepository;
import com.centros_sass.app.service.StorageConditionService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StorageConditionServiceImpl implements StorageConditionService {

    private final StorageConditionRepository storageConditionRepository;
    private final StorageConditionMapper storageConditionMapper;

    @Override
    @Transactional(readOnly = true)
    public Page<StorageConditionResponseDTO> findAll(Pageable pageable) {
        return storageConditionRepository.findAll(pageable)
                .map(storageConditionMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<StorageConditionResponseDTO> findById(Integer id) {
        return storageConditionRepository.findById(id)
                .map(storageConditionMapper::toResponse);
    }

    @Override
    @Transactional
    public StorageConditionResponseDTO save(StorageConditionRequestDTO dto) {
        if (storageConditionRepository.existsByStorageName(dto.storageName())) {
            throw new BadRequestException("Ya existe un condición de almacenamiento con el nombre: " + dto.storageName());
        }

        StorageCondition entity = storageConditionMapper.toEntity(dto);
        StorageCondition saved = storageConditionRepository.save(entity);
        return storageConditionMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public Optional<StorageConditionResponseDTO> update(Integer id, StorageConditionUpdateDTO dto) {
        return storageConditionRepository.findById(id).map(existing -> {
            if (dto.getStorageName() != null && !dto.getStorageName().isBlank()) {
                boolean duplicado = storageConditionRepository.existsByStorageNameAndIdNot(dto.getStorageName(), id);
                if (duplicado) {
                    throw new BadRequestException("Ya existe un condición de almacenamiento con el nombre: " + dto.getStorageName());
                }
            }

            storageConditionMapper.updateFromDto(dto, existing);
            StorageCondition saved = storageConditionRepository.saveAndFlush(existing);
            return storageConditionMapper.toResponse(saved);
        });
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        StorageCondition entity = storageConditionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("StorageCondition", "id", id));

        if (!entity.getMedications().isEmpty()) {
            throw new BadRequestException(
                    "No se puede eliminar el condición de almacenamiento '" + entity.getStorageName() + "' porque está en uso");
        }

        storageConditionRepository.delete(entity);
    }

}

*/
