package com.centros_sass.app.service.impl.treatments;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.centros_sass.app.dto.treatments.UserAllergyRequestDTO;
import com.centros_sass.app.dto.treatments.UserAllergyResponseDTO;
import com.centros_sass.app.dto.treatments.UserAllergyUpdateDTO;
import com.centros_sass.app.exception.BadRequestException;
import com.centros_sass.app.exception.ResourceNotFoundException;
import com.centros_sass.app.mapper.treatments.UserAllergyMapper;
import com.centros_sass.app.model.catalogs.treatments.Allergy;
import com.centros_sass.app.model.treatments.Medication;
import com.centros_sass.app.model.treatments.UserAllergy;
import com.centros_sass.app.model.treatments.UserMedicalInfo;
import com.centros_sass.app.repository.catalogs.treatments.AllergyRepository;
import com.centros_sass.app.repository.treatments.MedicationRepository;
import com.centros_sass.app.repository.treatments.UserAllergyRepository;
import com.centros_sass.app.repository.treatments.UserMedicalInfoRepository;
import com.centros_sass.app.service.UserAllergyService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserAllergyServiceImpl implements UserAllergyService {

    private final UserAllergyRepository userAllergyRepository;
    private final UserMedicalInfoRepository userMedicalInfoRepository;
    private final AllergyRepository allergyRepository;
    private final MedicationRepository medicationRepository;
    private final UserAllergyMapper userAllergyMapper;

    @Override
    @Transactional(readOnly = true)
    public Page<UserAllergyResponseDTO> findAll(Pageable pageable) {
        return userAllergyRepository.findAllByIsActiveTrue(pageable)
                .map(userAllergyMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserAllergyResponseDTO> findAllInactive(Pageable pageable) {
        return userAllergyRepository.findAllByIsActiveFalse(pageable)
                .map(userAllergyMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserAllergyResponseDTO> findAllIncludingInactive(Pageable pageable) {
        return userAllergyRepository.findAll(pageable)
                .map(userAllergyMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UserAllergyResponseDTO> findById(Integer id) {
        return userAllergyRepository.findById(id)
                .map(userAllergyMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserAllergyResponseDTO> findByUserMedicalInfoId(Integer infoId, Pageable pageable) {
        return userAllergyRepository.findByUserMedicalInfoIdAndIsActiveTrue(infoId, pageable)
                .map(userAllergyMapper::toResponse);
    }

    @Override
    @Transactional
    public UserAllergyResponseDTO save(UserAllergyRequestDTO dto) {
        UserAllergy allergy = userAllergyMapper.toEntity(dto);
        assignRelations(allergy, dto.userMedicalInfoId(), dto.allergyId());
        assignMedications(allergy, dto.medicationIds());
        allergy.setIsActive(true);

        UserAllergy saved = userAllergyRepository.save(allergy);
        return userAllergyMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public Optional<UserAllergyResponseDTO> update(Integer id, UserAllergyUpdateDTO dto) {
        return userAllergyRepository.findById(id).map(existing -> {
            if (dto.getUserMedicalInfoId() != null) {
                UserMedicalInfo info = userMedicalInfoRepository.findById(dto.getUserMedicalInfoId())
                        .orElseThrow(() -> new ResourceNotFoundException("UserMedicalInfo", "id", dto.getUserMedicalInfoId()));
                existing.setUserMedicalInfo(info);
            }

            if (dto.getAllergyId() != null) {
                Allergy allergy = allergyRepository.findById(dto.getAllergyId())
                        .orElseThrow(() -> new ResourceNotFoundException("Allergy", "id", dto.getAllergyId()));
                existing.setAllergy(allergy);
            }

            if (dto.getMedicationIds() != null) {
                assignMedications(existing, dto.getMedicationIds());
            }

            userAllergyMapper.updateFromDto(dto, existing);
            UserAllergy saved = userAllergyRepository.saveAndFlush(existing);
            return userAllergyMapper.toResponse(saved);
        });
    }

    @Override
    @Transactional
    public Optional<UserAllergyResponseDTO> delete(Integer id) {
        return userAllergyRepository.findById(id).map(existing -> {
            existing.setIsActive(false);
            UserAllergy saved = userAllergyRepository.saveAndFlush(existing);
            return userAllergyMapper.toResponse(saved);
        });
    }

    private void assignRelations(UserAllergy allergy, Integer infoId, Integer allergyId) {
        UserMedicalInfo info = userMedicalInfoRepository.findById(infoId)
                .orElseThrow(() -> new ResourceNotFoundException("UserMedicalInfo", "id", infoId));
        allergy.setUserMedicalInfo(info);

        Allergy a = allergyRepository.findById(allergyId)
                .orElseThrow(() -> new ResourceNotFoundException("Allergy", "id", allergyId));
        allergy.setAllergy(a);
    }

    private void assignMedications(UserAllergy allergy, Set<Integer> medicationIds) {
        allergy.getMedications().clear();
        if (medicationIds != null && !medicationIds.isEmpty()) {
            List<Medication> medications = medicationRepository.findAllById(medicationIds);
            if (medications.size() != medicationIds.size()) {
                throw new BadRequestException("Uno o más medicamentos no existen");
            }
            medications.forEach(allergy.getMedications()::add);
        }
    }

}
