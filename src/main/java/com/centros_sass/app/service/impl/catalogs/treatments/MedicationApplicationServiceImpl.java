/*
package com.centros_sass.app.service.impl.catalogs.treatments;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.centros_sass.app.dto.catalogs.treatments.MedicationApplicationRequestDTO;
import com.centros_sass.app.dto.catalogs.treatments.MedicationApplicationResponseDTO;
import com.centros_sass.app.dto.catalogs.treatments.MedicationApplicationUpdateDTO;
import com.centros_sass.app.exception.BadRequestException;
import com.centros_sass.app.exception.ResourceNotFoundException;
import com.centros_sass.app.mapper.catalogs.treatments.MedicationApplicationMapper;
import com.centros_sass.app.model.catalogs.treatments.MedicationApplication;
import com.centros_sass.app.repository.catalogs.treatments.MedicationApplicationRepository;
import com.centros_sass.app.service.MedicationApplicationService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MedicationApplicationServiceImpl implements MedicationApplicationService {

    private final MedicationApplicationRepository medicationApplicationRepository;
    private final MedicationApplicationMapper medicationApplicationMapper;

    @Override
    @Transactional(readOnly = true)
    public Page<MedicationApplicationResponseDTO> findAll(Pageable pageable) {
        return medicationApplicationRepository.findAll(pageable)
                .map(medicationApplicationMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<MedicationApplicationResponseDTO> findById(Integer id) {
        return medicationApplicationRepository.findById(id)
                .map(medicationApplicationMapper::toResponse);
    }

    @Override
    @Transactional
    public MedicationApplicationResponseDTO save(MedicationApplicationRequestDTO dto) {
        if (medicationApplicationRepository.existsByMedicationApplicationName(dto.medicationApplicationName())) {
            throw new BadRequestException("Ya existe un aplicación de medicamento con el nombre: " + dto.medicationApplicationName());
        }

        MedicationApplication entity = medicationApplicationMapper.toEntity(dto);
        MedicationApplication saved = medicationApplicationRepository.save(entity);
        return medicationApplicationMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public Optional<MedicationApplicationResponseDTO> update(Integer id, MedicationApplicationUpdateDTO dto) {
        return medicationApplicationRepository.findById(id).map(existing -> {
            if (dto.getMedicationApplicationName() != null && !dto.getMedicationApplicationName().isBlank()) {
                boolean duplicado = medicationApplicationRepository.existsByMedicationApplicationNameAndIdNot(dto.getMedicationApplicationName(), id);
                if (duplicado) {
                    throw new BadRequestException("Ya existe un aplicación de medicamento con el nombre: " + dto.getMedicationApplicationName());
                }
            }

            medicationApplicationMapper.updateFromDto(dto, existing);
            MedicationApplication saved = medicationApplicationRepository.saveAndFlush(existing);
            return medicationApplicationMapper.toResponse(saved);
        });
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        MedicationApplication entity = medicationApplicationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("MedicationApplication", "id", id));

        if (!entity.getMedications().isEmpty()) {
            throw new BadRequestException(
                    "No se puede eliminar el aplicación de medicamento '" + entity.getMedicationApplicationName() + "' porque está en uso");
        }

        medicationApplicationRepository.delete(entity);
    }

}

*/
