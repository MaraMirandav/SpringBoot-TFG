package com.centros_sass.app.mapper;

import java.time.LocalDateTime;
import java.util.Set;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import com.centros_sass.app.dto.workerschedule.WorkerScheduleRequestDTO;
import com.centros_sass.app.dto.workerschedule.WorkerScheduleResponseDTO;
import com.centros_sass.app.dto.workerschedule.WorkerScheduleUpdateDTO;
import com.centros_sass.app.model.profiles.workers.Worker;
import com.centros_sass.app.model.profiles.workers.WorkerSchedule;
import com.centros_sass.app.model.profiles.workers.WorkerScheduleRecord;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface WorkerScheduleMapper {

    @Mapping(target = "workerId", source = "worker.id")
    @Mapping(target = "workerFullName", expression = "java(buildWorkerFullName(schedule))")
    @Mapping(target = "openDayId", source = "openDay.id")
    @Mapping(target = "dayName", source = "openDay.dayName")
    @Mapping(target = "hasRecord", expression = "java(hasActiveRecord(schedule))")
    @Mapping(target = "createdAt", expression = "java(formatDateTime(schedule.getCreatedAt()))")
    @Mapping(target = "updatedAt", expression = "java(formatDateTime(schedule.getUpdatedAt()))")
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

    default String buildWorkerFullName(WorkerSchedule schedule) {
        Worker worker = schedule.getWorker();
        if (worker == null) {
            return null;
        }
        StringBuilder fullName = new StringBuilder();
        fullName.append(worker.getFirstName());
        if (worker.getSecondName() != null) {
            fullName.append(" ").append(worker.getSecondName());
        }
        fullName.append(" ").append(worker.getFirstSurname());
        if (worker.getSecondSurname() != null) {
            fullName.append(" ").append(worker.getSecondSurname());
        }
        return fullName.toString();
    }

    default boolean hasActiveRecord(WorkerSchedule schedule) {
        Set<WorkerScheduleRecord> records = schedule.getRecords();
        return records != null && records.stream().anyMatch(WorkerScheduleRecord::getIsActive);
    }

    default String formatDateTime(LocalDateTime dateTime) {
        if (dateTime == null) {
            return null;
        }
        return dateTime.toString();
    }

}
