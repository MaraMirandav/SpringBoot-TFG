package com.centros_sass.app.service.impl.treatments;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.centros_sass.app.dto.treatments.MedicationRequestDTO;
import com.centros_sass.app.dto.treatments.MedicationResponseDTO;
import com.centros_sass.app.dto.treatments.MedicationUpdateDTO;
import com.centros_sass.app.exception.BadRequestException;
import com.centros_sass.app.exception.ResourceNotFoundException;
import com.centros_sass.app.mapper.treatments.MedicationMapper;
import com.centros_sass.app.model.catalogs.treatments.MedicationApplication;
import com.centros_sass.app.model.catalogs.treatments.MedicationName;
import com.centros_sass.app.model.catalogs.treatments.StorageCondition;
import com.centros_sass.app.model.profiles.users.User;
import com.centros_sass.app.model.treatments.Medication;
import com.centros_sass.app.repository.catalogs.treatments.MedicationApplicationRepository;
import com.centros_sass.app.repository.catalogs.treatments.MedicationNameRepository;
import com.centros_sass.app.repository.catalogs.treatments.StorageConditionRepository;
import com.centros_sass.app.repository.profiles.UserRepository;
import com.centros_sass.app.repository.treatments.MedicationRepository;
import com.centros_sass.app.service.MedicationService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MedicationServiceImpl implements MedicationService {

    private final MedicationRepository medicationRepository;
    private final UserRepository userRepository;
    private final MedicationNameRepository medicationNameRepository;
    private final StorageConditionRepository storageConditionRepository;
    private final MedicationApplicationRepository medicationApplicationRepository;
    private final MedicationMapper medicationMapper;

    @Override
    @Transactional(readOnly = true)
    public Page<MedicationResponseDTO> findAll(Pageable pageable) {
        return medicationRepository.findAllByIsActiveTrue(pageable)
                .map(medicationMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MedicationResponseDTO> findAllInactive(Pageable pageable) {
        return medicationRepository.findAllByIsActiveFalse(pageable)
                .map(medicationMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MedicationResponseDTO> findAllIncludingInactive(Pageable pageable) {
        return medicationRepository.findAll(pageable)
                .map(medicationMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<MedicationResponseDTO> findById(Integer id) {
        return medicationRepository.findById(id)
                .map(medicationMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MedicationResponseDTO> findByUserId(Integer userId, Pageable pageable) {
        return medicationRepository.findByUserIdAndIsActiveTrue(userId, pageable)
                .map(medicationMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MedicationResponseDTO> findExpiringSoon(Pageable pageable) {
        LocalDate threshold = LocalDate.now().plusDays(30);
        return medicationRepository.findByExpirationDateBeforeAndIsActiveTrue(threshold, pageable)
                .map(medicationMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MedicationResponseDTO> findLowStock(Pageable pageable) {
        return medicationRepository.findByStockLessThanEqualAndIsActiveTrue(5, pageable)
                .map(medicationMapper::toResponse);
    }

    @Override
    @Transactional
    public MedicationResponseDTO save(MedicationRequestDTO dto) {
        validateDates(dto.receptionDate(), dto.expirationDate());

        Medication medication = medicationMapper.toEntity(dto);
        assignRelations(medication, dto.userId(), dto.medicationNameId(), dto.storageConditionId(), dto.medicationApplicationId());
        medication.setStock(dto.stock());
        medication.setIsActive(true);

        Medication saved = medicationRepository.save(medication);
        return medicationMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public Optional<MedicationResponseDTO> update(Integer id, MedicationUpdateDTO dto) {
        return medicationRepository.findById(id).map(existing -> {
            if (dto.getReceptionDate() != null && dto.getExpirationDate() != null) {
                validateDates(dto.getReceptionDate(), dto.getExpirationDate());
            } else if (dto.getReceptionDate() != null && dto.getReceptionDate().isAfter(existing.getExpirationDate())) {
                throw new BadRequestException("La fecha de recepción debe ser anterior a la fecha de caducidad");
            } else if (dto.getExpirationDate() != null && dto.getExpirationDate().isBefore(existing.getReceptionDate())) {
                throw new BadRequestException("La fecha de caducidad debe ser posterior a la fecha de recepción");
            }

            medicationMapper.updateFromDto(dto, existing);

            if (dto.getUserId() != null) {
                User user = userRepository.findById(dto.getUserId())
                        .orElseThrow(() -> new ResourceNotFoundException("User", "id", dto.getUserId()));
                existing.setUser(user);
            }

            if (dto.getMedicationNameId() != null) {
                MedicationName name = medicationNameRepository.findById(dto.getMedicationNameId())
                        .orElseThrow(() -> new ResourceNotFoundException("MedicationName", "id", dto.getMedicationNameId()));
                existing.setMedicationName(name);
            }

            if (dto.getStorageConditionId() != null) {
                StorageCondition condition = storageConditionRepository.findById(dto.getStorageConditionId())
                        .orElseThrow(() -> new ResourceNotFoundException("StorageCondition", "id", dto.getStorageConditionId()));
                existing.setStorageCondition(condition);
            }

            if (dto.getMedicationApplicationId() != null) {
                MedicationApplication application = medicationApplicationRepository.findById(dto.getMedicationApplicationId())
                        .orElseThrow(() -> new ResourceNotFoundException("MedicationApplication", "id", dto.getMedicationApplicationId()));
                existing.setMedicationApplication(application);
            }

            Medication saved = medicationRepository.saveAndFlush(existing);
            return medicationMapper.toResponse(saved);
        });
    }

    @Override
    @Transactional
    public Optional<MedicationResponseDTO> delete(Integer id) {
        return medicationRepository.findById(id).map(existing -> {
            existing.setIsActive(false);
            Medication saved = medicationRepository.saveAndFlush(existing);
            return medicationMapper.toResponse(saved);
        });
    }

    // Métodos Helpers

    private void validateDates(LocalDate receptionDate, LocalDate expirationDate) {
        if (receptionDate.isAfter(expirationDate) || receptionDate.isEqual(expirationDate)) {
            throw new BadRequestException("La fecha de recepción debe ser anterior a la fecha de caducidad");
        }
    }

    private void assignRelations(Medication medication, Integer userId, Integer medicationNameId, Integer storageConditionId, Integer medicationApplicationId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        medication.setUser(user);

        MedicationName name = medicationNameRepository.findById(medicationNameId)
                .orElseThrow(() -> new ResourceNotFoundException("MedicationName", "id", medicationNameId));
        medication.setMedicationName(name);

        StorageCondition condition = storageConditionRepository.findById(storageConditionId)
                .orElseThrow(() -> new ResourceNotFoundException("StorageCondition", "id", storageConditionId));
        medication.setStorageCondition(condition);

        MedicationApplication application = medicationApplicationRepository.findById(medicationApplicationId)
                .orElseThrow(() -> new ResourceNotFoundException("MedicationApplication", "id", medicationApplicationId));
        medication.setMedicationApplication(application);
    }

}
