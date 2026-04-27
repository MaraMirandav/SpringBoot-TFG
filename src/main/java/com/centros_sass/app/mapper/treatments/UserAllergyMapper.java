package com.centros_sass.app.mapper.treatments;

import java.util.Set;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import com.centros_sass.app.dto.treatments.UserAllergyRequestDTO;
import com.centros_sass.app.dto.treatments.UserAllergyResponseDTO;
import com.centros_sass.app.dto.treatments.UserAllergyUpdateDTO;
import com.centros_sass.app.model.treatments.Medication;
import com.centros_sass.app.model.treatments.UserAllergy;
import com.centros_sass.app.utils.MapperHelper;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    imports = MapperHelper.class
)
public interface UserAllergyMapper {

    @Mapping(target = "userMedicalInfoId", source = "userMedicalInfo.id")
    @Mapping(target = "userFullName", expression = "java(MapperHelper.buildFullName(entity.getUserMedicalInfo() != null ? entity.getUserMedicalInfo().getUser() : null))")
    @Mapping(target = "allergyId", source = "allergy.id")
    @Mapping(target = "allergyName", source = "allergy.allergyName")
    @Mapping(target = "medicationIds", expression = "java(extractMedicationIds(entity))")
    @Mapping(target = "createdAt", expression = "java(MapperHelper.formatDateTime(entity.getCreatedAt()))")
    @Mapping(target = "updatedAt", expression = "java(MapperHelper.formatDateTime(entity.getUpdatedAt()))")
    UserAllergyResponseDTO toResponse(UserAllergy entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "isActive", ignore = true)
    @Mapping(target = "userMedicalInfo", ignore = true)
    @Mapping(target = "allergy", ignore = true)
    @Mapping(target = "medications", ignore = true)
    UserAllergy toEntity(UserAllergyRequestDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userMedicalInfo", ignore = true)
    @Mapping(target = "allergy", ignore = true)
    @Mapping(target = "medications", ignore = true)
    void updateFromDto(UserAllergyUpdateDTO dto, @MappingTarget UserAllergy entity);

    default Set<Integer> extractMedicationIds(UserAllergy entity) {
        Set<Medication> medications = entity.getMedications();
        if (medications == null) {
            return Set.of();
        }
        return medications.stream()
                .map(Medication::getId)
                .collect(Collectors.toSet());
    }

}
