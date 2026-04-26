/*
package com.centros_sass.app.service.impl.catalogs.treatments;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.centros_sass.app.dto.catalogs.treatments.MedicationNameRequestDTO;
import com.centros_sass.app.dto.catalogs.treatments.MedicationNameResponseDTO;
import com.centros_sass.app.dto.catalogs.treatments.MedicationNameUpdateDTO;
import com.centros_sass.app.exception.BadRequestException;
import com.centros_sass.app.exception.ResourceNotFoundException;
import com.centros_sass.app.mapper.catalogs.treatments.MedicationNameMapper;
import com.centros_sass.app.model.catalogs.treatments.MedicationName;
import com.centros_sass.app.repository.catalogs.treatments.MedicationNameRepository;
import com.centros_sass.app.service.MedicationNameService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MedicationNameServiceImpl implements MedicationNameService {

    private final MedicationNameRepository medicationNameRepository;
    private final MedicationNameMapper medicationNameMapper;

    @Override
    @Transactional(readOnly = true)
    public Page<MedicationNameResponseDTO> findAll(Pageable pageable) {
        return medicationNameRepository.findAll(pageable)
                .map(medicationNameMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<MedicationNameResponseDTO> findById(Integer id) {
        return medicationNameRepository.findById(id)
                .map(medicationNameMapper::toResponse);
    }

    @Override
    @Transactional
    public MedicationNameResponseDTO save(MedicationNameRequestDTO dto) {
        if (medicationNameRepository.existsByMedicationName(dto.medicationName())) {
            throw new BadRequestException("Ya existe un medicamento con el nombre: " + dto.medicationName());
        }

        MedicationName entity = medicationNameMapper.toEntity(dto);
        MedicationName saved = medicationNameRepository.save(entity);
        return medicationNameMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public Optional<MedicationNameResponseDTO> update(Integer id, MedicationNameUpdateDTO dto) {
        return medicationNameRepository.findById(id).map(existing -> {
            if (dto.getMedicationName() != null && !dto.getMedicationName().isBlank()) {
                boolean duplicado = medicationNameRepository.existsByMedicationNameAndIdNot(dto.getMedicationName(), id);
                if (duplicado) {
                    throw new BadRequestException("Ya existe un medicamento con el nombre: " + dto.getMedicationName());
                }
            }

            medicationNameMapper.updateFromDto(dto, existing);
            MedicationName saved = medicationNameRepository.saveAndFlush(existing);
            return medicationNameMapper.toResponse(saved);
        });
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        MedicationName entity = medicationNameRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("MedicationName", "id", id));

        if (!entity.getMedications().isEmpty()) {
            throw new BadRequestException(
                    "No se puede eliminar el medicamento '" + entity.getMedicationName() + "' porque está en uso");
        }

        medicationNameRepository.delete(entity);
    }

}

*/
