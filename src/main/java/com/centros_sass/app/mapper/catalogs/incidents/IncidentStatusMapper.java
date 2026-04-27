package com.centros_sass.app.mapper.catalogs.incidents;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import com.centros_sass.app.dto.catalogs.incidents.IncidentStatusRequestDTO;
import com.centros_sass.app.dto.catalogs.incidents.IncidentStatusResponseDTO;
import com.centros_sass.app.dto.catalogs.incidents.IncidentStatusUpdateDTO;
import com.centros_sass.app.model.catalogs.incidents.IncidentStatus;
import com.centros_sass.app.utils.MapperHelper;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    imports = MapperHelper.class
)
public interface IncidentStatusMapper {

    @Mapping(target = "createdAt", expression = "java(MapperHelper.formatDateTime(entity.getCreatedAt()))")
    @Mapping(target = "updatedAt", expression = "java(MapperHelper.formatDateTime(entity.getUpdatedAt()))")
    IncidentStatusResponseDTO toResponse(IncidentStatus entity);

    @Mapping(target = "id", ignore = true)
    IncidentStatus toEntity(IncidentStatusRequestDTO dto);

    @Mapping(target = "id", ignore = true)
    void updateFromDto(IncidentStatusUpdateDTO dto, @MappingTarget IncidentStatus entity);

}
