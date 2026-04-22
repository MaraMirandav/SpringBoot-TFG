package com.centros_sass.app.mapper;

import java.util.Set;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import com.centros_sass.app.dto.worker.WorkerRequestDTO;
import com.centros_sass.app.dto.worker.WorkerResponseDTO;
import com.centros_sass.app.dto.worker.WorkerUpdateDTO;
import com.centros_sass.app.model.catalogs.fixed.organization.Role;
import com.centros_sass.app.model.profiles.workers.Worker;
import com.centros_sass.app.utils.MapperHelper;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    imports = MapperHelper.class
)
public interface WorkerMapper {

    @Mapping(target = "roleIds", expression = "java(extractRoleIds(worker))")
    @Mapping(target = "scheduleCount", expression = "java(worker.getSchedules() != null ? worker.getSchedules().size() : 0)")
    @Mapping(target = "createdAt", expression = "java(MapperHelper.formatDateTime(worker.getCreatedAt()))")
    @Mapping(target = "updatedAt", expression = "java(MapperHelper.formatDateTime(worker.getUpdatedAt()))")
    WorkerResponseDTO toResponse(Worker worker);

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

    // Métodos Helpers

    default Set<Integer> extractRoleIds(Worker worker) {
        Set<Role> roles = worker.getRoles();
        if (roles == null) {
            return Set.of();
        }
        return roles.stream()
                .map(Role::getId)
                .collect(Collectors.toSet());
    }

}
