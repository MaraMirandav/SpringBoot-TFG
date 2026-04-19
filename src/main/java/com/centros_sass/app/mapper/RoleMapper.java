package com.centros_sass.app.mapper;

import java.time.LocalDateTime;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import com.centros_sass.app.dto.role.RoleRequestDTO;
import com.centros_sass.app.dto.role.RoleResponseDTO;
import com.centros_sass.app.dto.role.RoleUpdateDTO;
import com.centros_sass.app.model.catalogs.fixed.organization.Role;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface RoleMapper {

    @Mapping(target = "createdAt", expression = "java(formatDateTime(role.getCreatedAt()))")
    @Mapping(target = "updatedAt", expression = "java(formatDateTime(role.getUpdatedAt()))")
    RoleResponseDTO toResponse(Role role);

    @Mapping(target = "id", ignore = true)
    Role toEntity(RoleRequestDTO dto);

    @Mapping(target = "id", ignore = true)
    void updateFromDto(RoleUpdateDTO dto, @MappingTarget Role role);

    default String formatDateTime(LocalDateTime dateTime) {
        if (dateTime == null) {
            return null;
        }
        return dateTime.toString();
    }

}