package com.centros_sass.app.mapper.catalogs.incidents;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import com.centros_sass.app.dto.catalogs.incidents.UserIncidentTypeRequestDTO;
import com.centros_sass.app.dto.catalogs.incidents.UserIncidentTypeResponseDTO;
import com.centros_sass.app.dto.catalogs.incidents.UserIncidentTypeUpdateDTO;
import com.centros_sass.app.model.catalogs.incidents.user.UserIncidentType;
import com.centros_sass.app.utils.MapperHelper;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    imports = MapperHelper.class
)
public interface UserIncidentTypeMapper {

    @Mapping(target = "createdAt", expression = "java(MapperHelper.formatDateTime(entity.getCreatedAt()))")
    @Mapping(target = "updatedAt", expression = "java(MapperHelper.formatDateTime(entity.getUpdatedAt()))")
    UserIncidentTypeResponseDTO toResponse(UserIncidentType entity);

    @Mapping(target = "id", ignore = true)
    UserIncidentType toEntity(UserIncidentTypeRequestDTO dto);

    @Mapping(target = "id", ignore = true)
    void updateFromDto(UserIncidentTypeUpdateDTO dto, @MappingTarget UserIncidentType entity);

}
