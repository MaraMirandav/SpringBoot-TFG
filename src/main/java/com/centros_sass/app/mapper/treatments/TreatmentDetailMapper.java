package com.centros_sass.app.mapper.treatments;

import java.util.Set;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import com.centros_sass.app.dto.treatments.TreatmentDetailRequestDTO;
import com.centros_sass.app.dto.treatments.TreatmentDetailResponseDTO;
import com.centros_sass.app.dto.treatments.TreatmentDetailUpdateDTO;
import com.centros_sass.app.model.treatments.Medication;
import com.centros_sass.app.model.treatments.TreatmentDetail;
import com.centros_sass.app.model.treatments.UserIllness;
import com.centros_sass.app.utils.MapperHelper;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    imports = MapperHelper.class
)
public interface TreatmentDetailMapper {

    @Mapping(target = "medicationIds", expression = "java(extractMedicationIds(entity))")
    @Mapping(target = "userIllnessIds", expression = "java(extractUserIllnessIds(entity))")
    @Mapping(target = "createdAt", expression = "java(MapperHelper.formatDateTime(entity.getCreatedAt()))")
    @Mapping(target = "updatedAt", expression = "java(MapperHelper.formatDateTime(entity.getUpdatedAt()))")
    TreatmentDetailResponseDTO toResponse(TreatmentDetail entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "isActive", ignore = true)
    @Mapping(target = "medications", ignore = true)
    @Mapping(target = "userIllnesses", ignore = true)
    TreatmentDetail toEntity(TreatmentDetailRequestDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "medications", ignore = true)
    @Mapping(target = "userIllnesses", ignore = true)
    void updateFromDto(TreatmentDetailUpdateDTO dto, @MappingTarget TreatmentDetail entity);

    default Set<Integer> extractMedicationIds(TreatmentDetail entity) {
        Set<Medication> medications = entity.getMedications();
        if (medications == null) {
            return Set.of();
        }
        return medications.stream()
                .map(Medication::getId)
                .collect(Collectors.toSet());
    }

    default Set<Integer> extractUserIllnessIds(TreatmentDetail entity) {
        Set<UserIllness> illnesses = entity.getUserIllnesses();
        if (illnesses == null) {
            return Set.of();
        }
        return illnesses.stream()
                .map(UserIllness::getId)
                .collect(Collectors.toSet());
    }

}
