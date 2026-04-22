package com.centros_sass.app.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import com.centros_sass.app.dto.vehicle.RouteVehicleRequestDTO;
import com.centros_sass.app.dto.vehicle.RouteVehicleResponseDTO;
import com.centros_sass.app.dto.vehicle.RouteVehicleUpdateDTO;
import com.centros_sass.app.model.transport.RouteVehicle;
import com.centros_sass.app.utils.MapperHelper;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    imports = MapperHelper.class
)
public interface RouteVehicleMapper {

    @Mapping(target = "createdAt", expression = "java(MapperHelper.formatDateTime(vehicle.getCreatedAt()))")
    @Mapping(target = "updatedAt", expression = "java(MapperHelper.formatDateTime(vehicle.getUpdatedAt()))")
    RouteVehicleResponseDTO toResponse(RouteVehicle vehicle);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "isActive", ignore = true)
    RouteVehicle toEntity(RouteVehicleRequestDTO dto);

    @Mapping(target = "id", ignore = true)
    void updateFromDto(RouteVehicleUpdateDTO dto, @MappingTarget RouteVehicle vehicle);

}
