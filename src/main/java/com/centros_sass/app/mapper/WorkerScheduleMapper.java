package com.centros_sass.app.mapper;

import java.util.Set;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import com.centros_sass.app.dto.workerschedule.WorkerScheduleRequestDTO;
import com.centros_sass.app.dto.workerschedule.WorkerScheduleResponseDTO;
import com.centros_sass.app.dto.workerschedule.WorkerScheduleUpdateDTO;
import com.centros_sass.app.model.profiles.workers.WorkerSchedule;
import com.centros_sass.app.model.profiles.workers.WorkerScheduleRecord;
import com.centros_sass.app.utils.MapperHelper;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    imports = MapperHelper.class
)
public interface WorkerScheduleMapper {

    @Mapping(target = "workerId", source = "worker.id")
    @Mapping(target = "workerFullName", expression = "java(MapperHelper.buildFullName(schedule.getWorker()))")
    @Mapping(target = "openDayId", source = "openDay.id")
    @Mapping(target = "dayName", source = "openDay.dayName")
    @Mapping(target = "hasRecord", expression = "java(hasActiveRecord(schedule))")
    @Mapping(target = "createdAt", expression = "java(MapperHelper.formatDateTime(schedule.getCreatedAt()))")
    @Mapping(target = "updatedAt", expression = "java(MapperHelper.formatDateTime(schedule.getUpdatedAt()))")
    WorkerScheduleResponseDTO toResponse(WorkerSchedule schedule);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "isActive", ignore = true)
    @Mapping(target = "worker", ignore = true)
    @Mapping(target = "openDay", ignore = true)
    @Mapping(target = "records", ignore = true)
    WorkerSchedule toEntity(WorkerScheduleRequestDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "worker", ignore = true)
    @Mapping(target = "openDay", ignore = true)
    @Mapping(target = "records", ignore = true)
    void updateFromDto(WorkerScheduleUpdateDTO dto, @MappingTarget WorkerSchedule entity);

    // Métodos Helpers

    default boolean hasActiveRecord(WorkerSchedule schedule) {
        Set<WorkerScheduleRecord> records = schedule.getRecords();
        return records != null && records.stream().anyMatch(WorkerScheduleRecord::getIsActive);
    }

}
