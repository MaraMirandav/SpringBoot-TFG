package com.centros_sass.app.mapper.transport;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import com.centros_sass.app.dto.transport.TransportRouteUserRequestDTO;
import com.centros_sass.app.dto.transport.TransportRouteUserResponseDTO;
import com.centros_sass.app.dto.transport.TransportRouteUserUpdateDTO;
import com.centros_sass.app.model.transport.TransportRouteUser;
import com.centros_sass.app.utils.MapperHelper;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    imports = MapperHelper.class
)
public interface TransportRouteUserMapper {

    @Mapping(target = "userId", expression = "java(entity.getUser() != null ? entity.getUser().getId() : null)")
    @Mapping(target = "userFullName", expression = "java(MapperHelper.buildFullName(entity.getUser()))")
    @Mapping(target = "createdAt", expression = "java(MapperHelper.formatDateTime(entity.getCreatedAt()))")
    @Mapping(target = "updatedAt", expression = "java(MapperHelper.formatDateTime(entity.getUpdatedAt()))")
    TransportRouteUserResponseDTO toResponse(TransportRouteUser entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "route", ignore = true)
    @Mapping(target = "user", ignore = true)
    TransportRouteUser toEntity(TransportRouteUserRequestDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "route", ignore = true)
    @Mapping(target = "user", ignore = true)
    void updateFromDto(TransportRouteUserUpdateDTO dto, @MappingTarget TransportRouteUser entity);
}
