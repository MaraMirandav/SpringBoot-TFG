package com.centros_sass.app.mapper.catalogs.incidents;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import com.centros_sass.app.dto.catalogs.incidents.CdIncidentTypeRequestDTO;
import com.centros_sass.app.dto.catalogs.incidents.CdIncidentTypeResponseDTO;
import com.centros_sass.app.dto.catalogs.incidents.CdIncidentTypeUpdateDTO;
import com.centros_sass.app.model.catalogs.incidents.center.CdIncidentType;
import com.centros_sass.app.utils.MapperHelper;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    imports = MapperHelper.class
)
public interface CdIncidentTypeMapper {

    @Mapping(target = "createdAt", expression = "java(MapperHelper.formatDateTime(entity.getCreatedAt()))")
    @Mapping(target = "updatedAt", expression = "java(MapperHelper.formatDateTime(entity.getUpdatedAt()))")
    CdIncidentTypeResponseDTO toResponse(CdIncidentType entity);

    @Mapping(target = "id", ignore = true)
    CdIncidentType toEntity(CdIncidentTypeRequestDTO dto);

    @Mapping(target = "id", ignore = true)
    void updateFromDto(CdIncidentTypeUpdateDTO dto, @MappingTarget CdIncidentType entity);

}
