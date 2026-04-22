package com.centros_sass.app.mapper;

import java.util.Set;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import com.centros_sass.app.dto.transport.TransportRouteRequestDTO;
import com.centros_sass.app.dto.transport.TransportRouteResponseDTO;
import com.centros_sass.app.dto.transport.TransportRouteUpdateDTO;
import com.centros_sass.app.model.profiles.users.User;
import com.centros_sass.app.model.transport.TransportRoute;
import com.centros_sass.app.utils.MapperHelper;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    imports = MapperHelper.class
)
public interface TransportRouteMapper {

    @Mapping(target = "routeShiftName", expression = "java(route.getRouteShift() != null ? route.getRouteShift().getRouteName() : null)")
    @Mapping(target = "licensePlate", expression = "java(route.getRouteVehicle() != null ? route.getRouteVehicle().getLicensePlate() : null)")
    @Mapping(target = "driverFullName", expression = "java(MapperHelper.buildFullName(route.getDriver()))")
    @Mapping(target = "copilotFullName", expression = "java(MapperHelper.buildFullName(route.getCopilot()))")
    @Mapping(target = "userIds", expression = "java(extractUserIds(route))")
    @Mapping(target = "userCount", expression = "java(route.getUsers() != null ? route.getUsers().size() : 0)")
    @Mapping(target = "createdAt", expression = "java(MapperHelper.formatDateTime(route.getCreatedAt()))")
    @Mapping(target = "updatedAt", expression = "java(MapperHelper.formatDateTime(route.getUpdatedAt()))")
    TransportRouteResponseDTO toResponse(TransportRoute route);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "isActive", ignore = true)
    @Mapping(target = "routeShift", ignore = true)
    @Mapping(target = "routeVehicle", ignore = true)
    @Mapping(target = "driver", ignore = true)
    @Mapping(target = "copilot", ignore = true)
    @Mapping(target = "users", ignore = true)
    TransportRoute toEntity(TransportRouteRequestDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "routeShift", ignore = true)
    @Mapping(target = "routeVehicle", ignore = true)
    @Mapping(target = "driver", ignore = true)
    @Mapping(target = "copilot", ignore = true)
    @Mapping(target = "users", ignore = true)
    void updateFromDto(TransportRouteUpdateDTO dto, @MappingTarget TransportRoute route);

    default Set<Integer> extractUserIds(TransportRoute route) {
        Set<User> users = route.getUsers();
        if (users == null) {
            return Set.of();
        }
        return users.stream()
                .map(User::getId)
                .collect(Collectors.toSet());
    }

}
