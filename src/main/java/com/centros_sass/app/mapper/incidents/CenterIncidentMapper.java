package com.centros_sass.app.mapper.incidents;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import com.centros_sass.app.dto.incidents.CenterIncidentRequestDTO;
import com.centros_sass.app.dto.incidents.CenterIncidentResponseDTO;
import com.centros_sass.app.dto.incidents.CenterIncidentUpdateDTO;
import com.centros_sass.app.model.incidents.center.CenterIncident;
import com.centros_sass.app.utils.MapperHelper;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    imports = MapperHelper.class
)
public interface CenterIncidentMapper {

    @Mapping(target = "reportedById", source = "reportedBy.id")
    @Mapping(target = "reportedByFullName", expression = "java(MapperHelper.buildFullName(centerIncident.getReportedBy()))")
    @Mapping(target = "incidentStatusId", source = "incidentStatus.id")
    @Mapping(target = "incidentStatusName", source = "incidentStatus.statusName")
    @Mapping(target = "cdIncidentTypeId", source = "cdIncident.id")
    @Mapping(target = "cdIncidentTypeName", source = "cdIncident.incidentName")
    @Mapping(target = "cdSignificanceTypeId", source = "cdSignificance.id")
    @Mapping(target = "cdSignificanceTypeName", source = "cdSignificance.significanceName")
    @Mapping(target = "commentCount", expression = "java(centerIncident.getCenterIncidentComments() != null ? centerIncident.getCenterIncidentComments().size() : 0)")
    @Mapping(target = "createdAt", expression = "java(MapperHelper.formatDateTime(centerIncident.getCreatedAt()))")
    @Mapping(target = "updatedAt", expression = "java(MapperHelper.formatDateTime(centerIncident.getUpdatedAt()))")
    CenterIncidentResponseDTO toResponse(CenterIncident centerIncident);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "isActive", ignore = true)
    @Mapping(target = "reportedBy", ignore = true)
    @Mapping(target = "incidentStatus", ignore = true)
    @Mapping(target = "cdIncident", ignore = true)
    @Mapping(target = "cdSignificance", ignore = true)
    @Mapping(target = "centerIncidentComments", ignore = true)
    CenterIncident toEntity(CenterIncidentRequestDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "reportedBy", ignore = true)
    @Mapping(target = "incidentStatus", ignore = true)
    @Mapping(target = "cdIncident", ignore = true)
    @Mapping(target = "cdSignificance", ignore = true)
    @Mapping(target = "centerIncidentComments", ignore = true)
    void updateFromDto(CenterIncidentUpdateDTO dto, @MappingTarget CenterIncident centerIncident);

}
