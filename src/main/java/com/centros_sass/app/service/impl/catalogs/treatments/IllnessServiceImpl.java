package com.centros_sass.app.service.impl.catalogs.treatments;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.centros_sass.app.dto.catalogs.treatments.IllnessRequestDTO;
import com.centros_sass.app.dto.catalogs.treatments.IllnessResponseDTO;
import com.centros_sass.app.dto.catalogs.treatments.IllnessUpdateDTO;
import com.centros_sass.app.exception.BadRequestException;
import com.centros_sass.app.exception.ResourceNotFoundException;
import com.centros_sass.app.mapper.catalogs.treatments.IllnessMapper;
import com.centros_sass.app.model.catalogs.treatments.Illness;
import com.centros_sass.app.repository.catalogs.treatments.IllnessRepository;
import com.centros_sass.app.service.IllnessService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IllnessServiceImpl implements IllnessService {

    private final IllnessRepository illnessRepository;
    private final IllnessMapper illnessMapper;

    @Override
    @Transactional(readOnly = true)
    public Page<IllnessResponseDTO> findAll(Pageable pageable) {
        return illnessRepository.findAll(pageable)
                .map(illnessMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<IllnessResponseDTO> findById(Integer id) {
        return illnessRepository.findById(id)
                .map(illnessMapper::toResponse);
    }

    @Override
    @Transactional
    public IllnessResponseDTO save(IllnessRequestDTO dto) {
        if (illnessRepository.existsByIllnessName(dto.illnessName())) {
            throw new BadRequestException("Ya existe un enfermedad con el nombre: " + dto.illnessName());
        }

        Illness entity = illnessMapper.toEntity(dto);
        Illness saved = illnessRepository.save(entity);
        return illnessMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public Optional<IllnessResponseDTO> update(Integer id, IllnessUpdateDTO dto) {
        return illnessRepository.findById(id).map(existing -> {
            if (dto.getIllnessName() != null && !dto.getIllnessName().isBlank()) {
                boolean duplicado = illnessRepository.existsByIllnessNameAndIdNot(dto.getIllnessName(), id);
                if (duplicado) {
                    throw new BadRequestException("Ya existe un enfermedad con el nombre: " + dto.getIllnessName());
                }
            }

            illnessMapper.updateFromDto(dto, existing);
            Illness saved = illnessRepository.saveAndFlush(existing);
            return illnessMapper.toResponse(saved);
        });
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        Illness entity = illnessRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Illness", "id", id));

        if (!entity.getUserIllnesses().isEmpty()) {
            throw new BadRequestException(
                    "No se puede eliminar el enfermedad '" + entity.getIllnessName() + "' porque está en uso");
        }

        illnessRepository.delete(entity);
    }

}

