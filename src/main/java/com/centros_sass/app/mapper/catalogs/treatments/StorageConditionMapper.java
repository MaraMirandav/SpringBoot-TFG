package com.centros_sass.app.mapper.catalogs.treatments;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import com.centros_sass.app.dto.catalogs.treatments.StorageConditionRequestDTO;
import com.centros_sass.app.dto.catalogs.treatments.StorageConditionResponseDTO;
import com.centros_sass.app.dto.catalogs.treatments.StorageConditionUpdateDTO;
import com.centros_sass.app.model.catalogs.treatments.StorageCondition;
import com.centros_sass.app.utils.MapperHelper;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    imports = MapperHelper.class
)
public interface StorageConditionMapper {

    @Mapping(target = "createdAt", expression = "java(MapperHelper.formatDateTime(entity.getCreatedAt()))")
    @Mapping(target = "updatedAt", expression = "java(MapperHelper.formatDateTime(entity.getUpdatedAt()))")
    StorageConditionResponseDTO toResponse(StorageCondition entity);

    @Mapping(target = "id", ignore = true)
    StorageCondition toEntity(StorageConditionRequestDTO dto);

    @Mapping(target = "id", ignore = true)
    void updateFromDto(StorageConditionUpdateDTO dto, @MappingTarget StorageCondition entity);

}
