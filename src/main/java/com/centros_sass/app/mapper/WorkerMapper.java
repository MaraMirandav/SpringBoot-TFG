package com.centros_sass.app.mapper;

import java.util.Set;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import com.centros_sass.app.dto.worker.WorkerRequestDTO;
import com.centros_sass.app.dto.worker.WorkerResponseDTO;
import com.centros_sass.app.dto.worker.WorkerUpdateDTO;
import com.centros_sass.app.model.catalogs.fixed.organization.Role;
import com.centros_sass.app.model.profiles.workers.Worker;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface WorkerMapper {

    @Mapping(target = "roleIds", expression = "java(entityToRoleIds(entity))")
    @Mapping(target = "scheduleCount", expression = "java(entity.getSchedules() != null ? entity.getSchedules().size() : 0)")
    @Mapping(target = "createdAt", expression = "java(formatDateTime(entity.getCreatedAt()))")
    @Mapping(target = "createdBy", expression = "java(entity.getCreatedBy())")
    @Mapping(target = "updatedAt", expression = "java(formatDateTime(entity.getUpdatedAt()))")
    @Mapping(target = "updatedBy", expression = "java(entity.getUpdatedBy())")
    WorkerResponseDTO toResponse(Worker entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "isActive", ignore = true)
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "schedules", ignore = true)
    @Mapping(target = "userMedicalInfos", ignore = true)
    Worker toEntity(WorkerRequestDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "schedules", ignore = true)
    @Mapping(target = "userMedicalInfos", ignore = true)
    void updateFromDto(WorkerUpdateDTO dto, @MappingTarget Worker entity);

    // --- Helpers ---

    default Set<Integer> entityToRoleIds(Worker entity) {
        if (entity.getRoles() == null) {
            return Set.of();
        }
        return entity.getRoles().stream()
                .map(Role::getId)
                .collect(Collectors.toSet());
    }

    default String formatDateTime(java.time.LocalDateTime dateTime) {
        if (dateTime == null) {
            return null;
        }
        return dateTime.toString();
    }

}
