package com.centros_sass.app.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import com.centros_sass.app.dto.bathroom.BathroomTaskRequestDTO;
import com.centros_sass.app.dto.bathroom.BathroomTaskResponseDTO;
import com.centros_sass.app.dto.bathroom.BathroomTaskUpdateDTO;
import com.centros_sass.app.model.catalogs.dynamic.bathroom.BathroomTask;
import com.centros_sass.app.utils.MapperHelper;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    imports = MapperHelper.class
)
public interface BathroomTaskMapper {

    @Mapping(target = "createdAt", expression = "java(MapperHelper.formatDateTime(task.getCreatedAt()))")
    @Mapping(target = "updatedAt", expression = "java(MapperHelper.formatDateTime(task.getUpdatedAt()))")
    BathroomTaskResponseDTO toResponse(BathroomTask task);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "isActive", ignore = true)
    @Mapping(target = "bathroomSchedules", ignore = true)
    BathroomTask toEntity(BathroomTaskRequestDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "bathroomSchedules", ignore = true)
    void updateFromDto(BathroomTaskUpdateDTO dto, @MappingTarget BathroomTask task);

}
