package com.centros_sass.app.mapper.catalogs.treatments;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import com.centros_sass.app.dto.catalogs.treatments.MedicationNameRequestDTO;
import com.centros_sass.app.dto.catalogs.treatments.MedicationNameResponseDTO;
import com.centros_sass.app.dto.catalogs.treatments.MedicationNameUpdateDTO;
import com.centros_sass.app.model.catalogs.treatments.MedicationName;
import com.centros_sass.app.utils.MapperHelper;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    imports = MapperHelper.class
)
public interface MedicationNameMapper {

    @Mapping(target = "createdAt", expression = "java(MapperHelper.formatDateTime(entity.getCreatedAt()))")
    @Mapping(target = "updatedAt", expression = "java(MapperHelper.formatDateTime(entity.getUpdatedAt()))")
    MedicationNameResponseDTO toResponse(MedicationName entity);

    @Mapping(target = "id", ignore = true)
    MedicationName toEntity(MedicationNameRequestDTO dto);

    @Mapping(target = "id", ignore = true)
    void updateFromDto(MedicationNameUpdateDTO dto, @MappingTarget MedicationName entity);

}
