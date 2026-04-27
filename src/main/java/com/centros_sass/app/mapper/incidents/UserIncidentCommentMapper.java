package com.centros_sass.app.mapper.incidents;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import com.centros_sass.app.dto.incidents.UserIncidentCommentRequestDTO;
import com.centros_sass.app.dto.incidents.UserIncidentCommentResponseDTO;
import com.centros_sass.app.dto.incidents.UserIncidentCommentUpdateDTO;
import com.centros_sass.app.model.incidents.user.UserIncidentComment;
import com.centros_sass.app.utils.MapperHelper;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    imports = MapperHelper.class
)
public interface UserIncidentCommentMapper {

    @Mapping(target = "workerId", source = "worker.id")
    @Mapping(target = "workerFullName", expression = "java(MapperHelper.buildFullName(comment.getWorker()))")
    @Mapping(target = "createdAt", expression = "java(MapperHelper.formatDateTime(comment.getCreatedAt()))")
    @Mapping(target = "updatedAt", expression = "java(MapperHelper.formatDateTime(comment.getUpdatedAt()))")
    UserIncidentCommentResponseDTO toResponse(UserIncidentComment comment);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "worker", ignore = true)
    @Mapping(target = "userIncident", ignore = true)
    UserIncidentComment toEntity(UserIncidentCommentRequestDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "worker", ignore = true)
    @Mapping(target = "userIncident", ignore = true)
    void updateFromDto(UserIncidentCommentUpdateDTO dto, @MappingTarget UserIncidentComment comment);

}
