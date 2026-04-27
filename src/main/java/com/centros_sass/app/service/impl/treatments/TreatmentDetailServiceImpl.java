package com.centros_sass.app.service.impl.treatments;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.centros_sass.app.dto.treatments.TreatmentDetailRequestDTO;
import com.centros_sass.app.dto.treatments.TreatmentDetailResponseDTO;
import com.centros_sass.app.dto.treatments.TreatmentDetailUpdateDTO;
import com.centros_sass.app.exception.BadRequestException;
import com.centros_sass.app.mapper.treatments.TreatmentDetailMapper;
import com.centros_sass.app.model.treatments.Medication;
import com.centros_sass.app.model.treatments.TreatmentDetail;
import com.centros_sass.app.model.treatments.UserIllness;
import com.centros_sass.app.repository.treatments.MedicationRepository;
import com.centros_sass.app.repository.treatments.TreatmentDetailRepository;
import com.centros_sass.app.repository.treatments.UserIllnessRepository;
import com.centros_sass.app.service.TreatmentDetailService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TreatmentDetailServiceImpl implements TreatmentDetailService {

    private final TreatmentDetailRepository treatmentDetailRepository;
    private final MedicationRepository medicationRepository;
    private final UserIllnessRepository userIllnessRepository;
    private final TreatmentDetailMapper treatmentDetailMapper;

    @Override
    @Transactional(readOnly = true)
    public Page<TreatmentDetailResponseDTO> findAll(Pageable pageable) {
        return treatmentDetailRepository.findAllByIsActiveTrue(pageable)
                .map(treatmentDetailMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TreatmentDetailResponseDTO> findAllInactive(Pageable pageable) {
        return treatmentDetailRepository.findAllByIsActiveFalse(pageable)
                .map(treatmentDetailMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TreatmentDetailResponseDTO> findAllIncludingInactive(Pageable pageable) {
        return treatmentDetailRepository.findAll(pageable)
                .map(treatmentDetailMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TreatmentDetailResponseDTO> findById(Integer id) {
        return treatmentDetailRepository.findById(id)
                .map(treatmentDetailMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TreatmentDetailResponseDTO> findActive(Pageable pageable) {
        return treatmentDetailRepository.findByEndDateIsNullAndIsActiveTrue(pageable)
                .map(treatmentDetailMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TreatmentDetailResponseDTO> findByMedicationId(Integer medicationId, Pageable pageable) {
        return treatmentDetailRepository.findByMedicationsIdAndIsActiveTrue(medicationId, pageable)
                .map(treatmentDetailMapper::toResponse);
    }

    @Override
    @Transactional
    public TreatmentDetailResponseDTO save(TreatmentDetailRequestDTO dto) {
        validateDates(dto.startDate(), dto.endDate());

        TreatmentDetail detail = treatmentDetailMapper.toEntity(dto);
        detail.setIsActive(true);

        TreatmentDetail saved = treatmentDetailRepository.save(detail);

        if (dto.medicationIds() != null && !dto.medicationIds().isEmpty()) {
            assignMedications(saved, dto.medicationIds());
        }

        if (dto.userIllnessIds() != null && !dto.userIllnessIds().isEmpty()) {
            assignUserIllnesses(saved, dto.userIllnessIds());
        }

        return treatmentDetailMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public Optional<TreatmentDetailResponseDTO> update(Integer id, TreatmentDetailUpdateDTO dto) {
        return treatmentDetailRepository.findById(id).map(existing -> {
            if (dto.getStartDate() != null && dto.getEndDate() != null) {
                validateDates(dto.getStartDate(), dto.getEndDate());
            } else if (dto.getEndDate() != null && dto.getEndDate().isBefore(existing.getStartDate())) {
                throw new BadRequestException("La fecha de fin debe ser posterior a la fecha de inicio");
            }

            treatmentDetailMapper.updateFromDto(dto, existing);

            if (dto.getMedicationIds() != null) {
                assignMedications(existing, dto.getMedicationIds());
            }

            if (dto.getUserIllnessIds() != null) {
                assignUserIllnesses(existing, dto.getUserIllnessIds());
            }

            TreatmentDetail saved = treatmentDetailRepository.saveAndFlush(existing);
            return treatmentDetailMapper.toResponse(saved);
        });
    }

    @Override
    @Transactional
    public Optional<TreatmentDetailResponseDTO> delete(Integer id) {
        return treatmentDetailRepository.findById(id).map(existing -> {
            existing.setIsActive(false);
            TreatmentDetail saved = treatmentDetailRepository.saveAndFlush(existing);
            return treatmentDetailMapper.toResponse(saved);
        });
    }

    private void validateDates(LocalDate startDate, LocalDate endDate) {
        if (endDate != null && (endDate.isBefore(startDate) || endDate.isEqual(startDate))) {
            throw new BadRequestException("La fecha de fin debe ser posterior a la fecha de inicio");
        }
    }

    private void assignMedications(TreatmentDetail detail, Set<Integer> medicationIds) {
        detail.getMedications().clear();
        if (!medicationIds.isEmpty()) {
            List<Medication> medications = medicationRepository.findAllById(medicationIds);
            if (medications.size() != medicationIds.size()) {
                throw new BadRequestException("Uno o más medicamentos no existen");
            }
            medications.forEach(detail.getMedications()::add);
        }
    }

    private void assignUserIllnesses(TreatmentDetail detail, Set<Integer> userIllnessIds) {
        detail.getUserIllnesses().clear();
        if (!userIllnessIds.isEmpty()) {
            List<UserIllness> illnesses = userIllnessRepository.findAllById(userIllnessIds);
            if (illnesses.size() != userIllnessIds.size()) {
                throw new BadRequestException("Una o más enfermedades de usuario no existen");
            }
            illnesses.forEach(detail.getUserIllnesses()::add);
        }
    }

}
