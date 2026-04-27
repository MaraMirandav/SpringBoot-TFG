package com.centros_sass.app.mapper.catalogs.transport;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import com.centros_sass.app.dto.catalogs.transport.RouteShiftRequestDTO;
import com.centros_sass.app.dto.catalogs.transport.RouteShiftResponseDTO;
import com.centros_sass.app.dto.catalogs.transport.RouteShiftUpdateDTO;
import com.centros_sass.app.model.catalogs.transport.RouteShift;
import com.centros_sass.app.utils.MapperHelper;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    imports = MapperHelper.class
)
public interface RouteShiftMapper {

    @Mapping(target = "createdAt", expression = "java(MapperHelper.formatDateTime(entity.getCreatedAt()))")
    @Mapping(target = "updatedAt", expression = "java(MapperHelper.formatDateTime(entity.getUpdatedAt()))")
    RouteShiftResponseDTO toResponse(RouteShift entity);

    @Mapping(target = "id", ignore = true)
    RouteShift toEntity(RouteShiftRequestDTO dto);

    @Mapping(target = "id", ignore = true)
    void updateFromDto(RouteShiftUpdateDTO dto, @MappingTarget RouteShift entity);

}
