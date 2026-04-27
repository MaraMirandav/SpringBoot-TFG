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

    @Mapping(target = "userId", expression = "java(medication.getUser() != null ? medication.getUser().getId() : null)")
    @Mapping(target = "userFullName", expression = "java(MapperHelper.buildFullName(medication.getUser()))")
    @Mapping(target = "medicationNameId", expression = "java(medication.getMedicationName() != null ? medication.getMedicationName().getId() : null)")
    @Mapping(target = "medicationName", expression = "java(medication.getMedicationName() != null ? medication.getMedicationName().getMedicationName() : null)")
    @Mapping(target = "storageConditionId", expression = "java(medication.getStorageCondition() != null ? medication.getStorageCondition().getId() : null)")
    @Mapping(target = "storageConditionName", expression = "java(medication.getStorageCondition() != null ? medication.getStorageCondition().getStorageName() : null)")
    @Mapping(target = "medicationApplicationId", expression = "java(medication.getMedicationApplication() != null ? medication.getMedicationApplication().getId() : null)")
    @Mapping(target = "medicationApplicationName", expression = "java(medication.getMedicationApplication() != null ? medication.getMedicationApplication().getMedicationApplicationName() : null)")
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
