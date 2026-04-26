package com.centros_sass.app.service.impl;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.centros_sass.app.dto.belongings.DiaperTypeRequestDTO;
import com.centros_sass.app.dto.belongings.DiaperTypeResponseDTO;
import com.centros_sass.app.dto.belongings.DiaperTypeUpdateDTO;
import com.centros_sass.app.exception.BadRequestException;
import com.centros_sass.app.mapper.DiaperTypeMapper;
import com.centros_sass.app.model.catalogs.dynamic.belongings.DiaperType;
import com.centros_sass.app.repository.DiaperTypeRepository;
import com.centros_sass.app.service.DiaperTypeService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DiaperTypeServiceImpl implements DiaperTypeService {

    private final DiaperTypeRepository diaperTypeRepository;
    private final DiaperTypeMapper diaperTypeMapper;

    @Override
    @Transactional(readOnly = true)
    public Page<DiaperTypeResponseDTO> findAll(Pageable pageable) {
        return diaperTypeRepository.findAllByIsActiveTrue(pageable)
                .map(diaperTypeMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DiaperTypeResponseDTO> findAllInactive(Pageable pageable) {
        return diaperTypeRepository.findAllByIsActiveFalse(pageable)
                .map(diaperTypeMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DiaperTypeResponseDTO> findAllIncludingInactive(Pageable pageable) {
        return diaperTypeRepository.findAll(pageable)
                .map(diaperTypeMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DiaperTypeResponseDTO> findById(Integer id) {
        return diaperTypeRepository.findById(id)
                .map(diaperTypeMapper::toResponse);
    }

    @Override
    @Transactional
    public DiaperTypeResponseDTO save(DiaperTypeRequestDTO dto) {
        if (diaperTypeRepository.existsByType(dto.type())) {
            throw new BadRequestException("Ya existe un tipo de pañal con el nombre: " + dto.type());
        }

        DiaperType type = diaperTypeMapper.toEntity(dto);
        DiaperType saved = diaperTypeRepository.save(type);
        return diaperTypeMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public Optional<DiaperTypeResponseDTO> update(Integer id, DiaperTypeUpdateDTO dto) {
        return diaperTypeRepository.findById(id).map(existing -> {
            if (dto.getType() != null && !dto.getType().isBlank()) {
                boolean duplicado = diaperTypeRepository.existsByTypeAndIdNot(dto.getType(), id);
                if (duplicado) {
                    throw new BadRequestException("Ya existe un tipo de pañal con el nombre: " + dto.getType());
                }
            }

            diaperTypeMapper.updateFromDto(dto, existing);
            DiaperType saved = diaperTypeRepository.saveAndFlush(existing);
            return diaperTypeMapper.toResponse(saved);
        });
    }

    @Override
    @Transactional
    public Optional<DiaperTypeResponseDTO> delete(Integer id) {
        return diaperTypeRepository.findById(id).map(existing -> {
            existing.setIsActive(false);
            DiaperType saved = diaperTypeRepository.saveAndFlush(existing);
            return diaperTypeMapper.toResponse(saved);
        });
    }

}
