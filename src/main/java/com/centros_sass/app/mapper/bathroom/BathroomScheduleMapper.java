package com.centros_sass.app.mapper.bathroom;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import com.centros_sass.app.dto.bathroom.BathroomScheduleRequestDTO;
import com.centros_sass.app.dto.bathroom.BathroomScheduleResponseDTO;
import com.centros_sass.app.dto.bathroom.BathroomScheduleUpdateDTO;
import com.centros_sass.app.model.bathroom.BathroomSchedule;
import com.centros_sass.app.utils.MapperHelper;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    imports = MapperHelper.class
)
public interface BathroomScheduleMapper {

    @Mapping(target = "userId", expression = "java(schedule.getUser() != null ? schedule.getUser().getId() : null)")
    @Mapping(target = "userFullName", expression = "java(MapperHelper.buildFullName(schedule.getUser()))")
    @Mapping(target = "bathroomTurnId", expression = "java(schedule.getBathroomTurn() != null ? schedule.getBathroomTurn().getId() : null)")
    @Mapping(target = "turnName", expression = "java(schedule.getBathroomTurn() != null ? schedule.getBathroomTurn().getTurnName() : null)")
    @Mapping(target = "bathroomTaskId", expression = "java(schedule.getBathroomTask() != null ? schedule.getBathroomTask().getId() : null)")
    @Mapping(target = "taskName", expression = "java(schedule.getBathroomTask() != null ? schedule.getBathroomTask().getTaskName() : null)")
    @Mapping(target = "createdAt", expression = "java(MapperHelper.formatDateTime(schedule.getCreatedAt()))")
    @Mapping(target = "updatedAt", expression = "java(MapperHelper.formatDateTime(schedule.getUpdatedAt()))")
    BathroomScheduleResponseDTO toResponse(BathroomSchedule schedule);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "isActive", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "bathroomTurn", ignore = true)
    @Mapping(target = "bathroomTask", ignore = true)
    BathroomSchedule toEntity(BathroomScheduleRequestDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "bathroomTurn", ignore = true)
    @Mapping(target = "bathroomTask", ignore = true)
    void updateFromDto(BathroomScheduleUpdateDTO dto, @MappingTarget BathroomSchedule schedule);

}
