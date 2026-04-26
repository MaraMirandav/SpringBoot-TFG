/*
package com.centros_sass.app.service.impl.catalogs.treatments;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.centros_sass.app.dto.catalogs.treatments.AllergyRequestDTO;
import com.centros_sass.app.dto.catalogs.treatments.AllergyResponseDTO;
import com.centros_sass.app.dto.catalogs.treatments.AllergyUpdateDTO;
import com.centros_sass.app.exception.BadRequestException;
import com.centros_sass.app.exception.ResourceNotFoundException;
import com.centros_sass.app.mapper.catalogs.treatments.AllergyMapper;
import com.centros_sass.app.model.catalogs.treatments.Allergy;
import com.centros_sass.app.repository.catalogs.treatments.AllergyRepository;
import com.centros_sass.app.service.AllergyService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AllergyServiceImpl implements AllergyService {

    private final AllergyRepository allergyRepository;
    private final AllergyMapper allergyMapper;

    @Override
    @Transactional(readOnly = true)
    public Page<AllergyResponseDTO> findAll(Pageable pageable) {
        return allergyRepository.findAll(pageable)
                .map(allergyMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AllergyResponseDTO> findById(Integer id) {
        return allergyRepository.findById(id)
                .map(allergyMapper::toResponse);
    }

    @Override
    @Transactional
    public AllergyResponseDTO save(AllergyRequestDTO dto) {
        if (allergyRepository.existsByAllergyName(dto.allergyName())) {
            throw new BadRequestException("Ya existe un alergia con el nombre: " + dto.allergyName());
        }

        Allergy entity = allergyMapper.toEntity(dto);
        Allergy saved = allergyRepository.save(entity);
        return allergyMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public Optional<AllergyResponseDTO> update(Integer id, AllergyUpdateDTO dto) {
        return allergyRepository.findById(id).map(existing -> {
            if (dto.getAllergyName() != null && !dto.getAllergyName().isBlank()) {
                boolean duplicado = allergyRepository.existsByAllergyNameAndIdNot(dto.getAllergyName(), id);
                if (duplicado) {
                    throw new BadRequestException("Ya existe un alergia con el nombre: " + dto.getAllergyName());
                }
            }

            allergyMapper.updateFromDto(dto, existing);
            Allergy saved = allergyRepository.saveAndFlush(existing);
            return allergyMapper.toResponse(saved);
        });
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        Allergy entity = allergyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Allergy", "id", id));

        if (!entity.getUserAllergies().isEmpty()) {
            throw new BadRequestException(
                    "No se puede eliminar el alergia '" + entity.getAllergyName() + "' porque está en uso");
        }

        allergyRepository.delete(entity);
    }

}

*/
