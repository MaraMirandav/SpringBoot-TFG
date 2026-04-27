package com.centros_sass.app.mapper.incidents;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import com.centros_sass.app.dto.incidents.CenterIncidentCommentRequestDTO;
import com.centros_sass.app.dto.incidents.CenterIncidentCommentResponseDTO;
import com.centros_sass.app.dto.incidents.CenterIncidentCommentUpdateDTO;
import com.centros_sass.app.model.incidents.center.CenterIncidentComment;
import com.centros_sass.app.utils.MapperHelper;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    imports = MapperHelper.class
)
public interface CenterIncidentCommentMapper {

    @Mapping(target = "workerId", source = "worker.id")
    @Mapping(target = "workerFullName", expression = "java(MapperHelper.buildFullName(comment.getWorker()))")
    @Mapping(target = "createdAt", expression = "java(MapperHelper.formatDateTime(comment.getCreatedAt()))")
    @Mapping(target = "updatedAt", expression = "java(MapperHelper.formatDateTime(comment.getUpdatedAt()))")
    CenterIncidentCommentResponseDTO toResponse(CenterIncidentComment comment);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "worker", ignore = true)
    @Mapping(target = "cdIncident", ignore = true)
    CenterIncidentComment toEntity(CenterIncidentCommentRequestDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "worker", ignore = true)
    @Mapping(target = "cdIncident", ignore = true)
    void updateFromDto(CenterIncidentCommentUpdateDTO dto, @MappingTarget CenterIncidentComment comment);

}
