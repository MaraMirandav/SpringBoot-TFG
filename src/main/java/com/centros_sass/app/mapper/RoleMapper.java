package com.centros_sass.app.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import com.centros_sass.app.dto.role.RoleRequestDTO;
import com.centros_sass.app.dto.role.RoleResponseDTO;
import com.centros_sass.app.dto.role.RoleUpdateDTO;
import com.centros_sass.app.model.catalogs.fixed.organization.Role;
import com.centros_sass.app.utils.MapperHelper;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    imports = MapperHelper.class
)
public interface RoleMapper {

    @Mapping(target = "createdAt", expression = "java(MapperHelper.formatDateTime(role.getCreatedAt()))")
    @Mapping(target = "updatedAt", expression = "java(MapperHelper.formatDateTime(role.getUpdatedAt()))")
    RoleResponseDTO toResponse(Role role);

    @Mapping(target = "id", ignore = true)
    Role toEntity(RoleRequestDTO dto);

    @Mapping(target = "id", ignore = true)
    void updateFromDto(RoleUpdateDTO dto, @MappingTarget Role role);

}
