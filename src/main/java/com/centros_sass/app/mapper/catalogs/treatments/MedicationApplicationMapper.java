package com.centros_sass.app.mapper.catalogs.treatments;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import com.centros_sass.app.dto.catalogs.treatments.MedicationApplicationRequestDTO;
import com.centros_sass.app.dto.catalogs.treatments.MedicationApplicationResponseDTO;
import com.centros_sass.app.dto.catalogs.treatments.MedicationApplicationUpdateDTO;
import com.centros_sass.app.model.catalogs.treatments.MedicationApplication;
import com.centros_sass.app.utils.MapperHelper;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    imports = MapperHelper.class
)
public interface MedicationApplicationMapper {

    @Mapping(target = "createdAt", expression = "java(MapperHelper.formatDateTime(entity.getCreatedAt()))")
    @Mapping(target = "updatedAt", expression = "java(MapperHelper.formatDateTime(entity.getUpdatedAt()))")
    MedicationApplicationResponseDTO toResponse(MedicationApplication entity);

    @Mapping(target = "id", ignore = true)
    MedicationApplication toEntity(MedicationApplicationRequestDTO dto);

    @Mapping(target = "id", ignore = true)
    void updateFromDto(MedicationApplicationUpdateDTO dto, @MappingTarget MedicationApplication entity);

}
