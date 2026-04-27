package com.centros_sass.app.mapper.treatments;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import com.centros_sass.app.dto.treatments.MedicationRequestDTO;
import com.centros_sass.app.dto.treatments.MedicationResponseDTO;
import com.centros_sass.app.dto.treatments.MedicationUpdateDTO;
import com.centros_sass.app.model.treatments.Medication;
import com.centros_sass.app.utils.MapperHelper;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    imports = MapperHelper.class
)
public interface MedicationMapper {

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "userFullName", expression = "java(MapperHelper.buildFullName(medication.getUser()))")
    @Mapping(target = "medicationNameId", source = "medicationName.id")
    @Mapping(target = "medicationName", source = "medicationName.medicationName")
    @Mapping(target = "storageConditionId", source = "storageCondition.id")
    @Mapping(target = "storageConditionName", source = "storageCondition.storageName")
    @Mapping(target = "medicationApplicationId", source = "medicationApplication.id")
    @Mapping(target = "medicationApplicationName", source = "medicationApplication.medicationApplicationName")
    @Mapping(target = "createdAt", expression = "java(MapperHelper.formatDateTime(medication.getCreatedAt()))")
    @Mapping(target = "updatedAt", expression = "java(MapperHelper.formatDateTime(medication.getUpdatedAt()))")
    MedicationResponseDTO toResponse(Medication medication);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "isActive", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "medicationName", ignore = true)
    @Mapping(target = "storageCondition", ignore = true)
    @Mapping(target = "medicationApplication", ignore = true)
    @Mapping(target = "treatmentDetails", ignore = true)
    @Mapping(target = "userAllergies", ignore = true)
    Medication toEntity(MedicationRequestDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "medicationName", ignore = true)
    @Mapping(target = "storageCondition", ignore = true)
    @Mapping(target = "medicationApplication", ignore = true)
    @Mapping(target = "treatmentDetails", ignore = true)
    @Mapping(target = "userAllergies", ignore = true)
    void updateFromDto(MedicationUpdateDTO dto, @MappingTarget Medication medication);

}
