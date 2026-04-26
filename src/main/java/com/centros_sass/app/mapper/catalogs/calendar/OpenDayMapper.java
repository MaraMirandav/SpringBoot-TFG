package com.centros_sass.app.mapper.catalogs.calendar;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import com.centros_sass.app.dto.catalogs.calendar.OpenDayRequestDTO;
import com.centros_sass.app.dto.catalogs.calendar.OpenDayResponseDTO;
import com.centros_sass.app.dto.catalogs.calendar.OpenDayUpdateDTO;
import com.centros_sass.app.model.catalogs.calendar.OpenDay;
import com.centros_sass.app.utils.MapperHelper;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    imports = MapperHelper.class
)
public interface OpenDayMapper {

    @Mapping(target = "createdAt", expression = "java(MapperHelper.formatDateTime(entity.getCreatedAt()))")
    @Mapping(target = "updatedAt", expression = "java(MapperHelper.formatDateTime(entity.getUpdatedAt()))")
    OpenDayResponseDTO toResponse(OpenDay entity);

    @Mapping(target = "id", ignore = true)
    OpenDay toEntity(OpenDayRequestDTO dto);

    @Mapping(target = "id", ignore = true)
    void updateFromDto(OpenDayUpdateDTO dto, @MappingTarget OpenDay entity);

}
