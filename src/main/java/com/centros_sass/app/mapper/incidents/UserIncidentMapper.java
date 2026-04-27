package com.centros_sass.app.mapper.incidents;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import com.centros_sass.app.dto.incidents.UserIncidentRequestDTO;
import com.centros_sass.app.dto.incidents.UserIncidentResponseDTO;
import com.centros_sass.app.dto.incidents.UserIncidentUpdateDTO;
import com.centros_sass.app.model.incidents.user.UserIncident;
import com.centros_sass.app.utils.MapperHelper;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    imports = MapperHelper.class
)
public interface UserIncidentMapper {

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "userFullName", expression = "java(MapperHelper.buildFullName(userIncident.getUser()))")
    @Mapping(target = "reportedById", source = "reportedBy.id")
    @Mapping(target = "reportedByFullName", expression = "java(MapperHelper.buildFullName(userIncident.getReportedBy()))")
    @Mapping(target = "incidentStatusId", source = "incidentStatus.id")
    @Mapping(target = "incidentStatusName", source = "incidentStatus.statusName")
    @Mapping(target = "userIncidentTypeId", source = "userIncident.id")
    @Mapping(target = "userIncidentTypeName", source = "userIncident.incidentName")
    @Mapping(target = "userSignificanceTypeId", source = "userSignificance.id")
    @Mapping(target = "userSignificanceTypeName", source = "userSignificance.significanceName")
    @Mapping(target = "commentCount", expression = "java(userIncident.getUserIncidentComments() != null ? userIncident.getUserIncidentComments().size() : 0)")
    @Mapping(target = "createdAt", expression = "java(MapperHelper.formatDateTime(userIncident.getCreatedAt()))")
    @Mapping(target = "updatedAt", expression = "java(MapperHelper.formatDateTime(userIncident.getUpdatedAt()))")
    UserIncidentResponseDTO toResponse(UserIncident userIncident);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "isActive", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "reportedBy", ignore = true)
    @Mapping(target = "incidentStatus", ignore = true)
    @Mapping(target = "userIncident", ignore = true)
    @Mapping(target = "userSignificance", ignore = true)
    @Mapping(target = "userIncidentComments", ignore = true)
    UserIncident toEntity(UserIncidentRequestDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "reportedBy", ignore = true)
    @Mapping(target = "incidentStatus", ignore = true)
    @Mapping(target = "userIncident", ignore = true)
    @Mapping(target = "userSignificance", ignore = true)
    @Mapping(target = "userIncidentComments", ignore = true)
    void updateFromDto(UserIncidentUpdateDTO dto, @MappingTarget UserIncident userIncident);

}
