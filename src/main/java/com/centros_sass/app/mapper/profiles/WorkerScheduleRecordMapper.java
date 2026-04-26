package com.centros_sass.app.mapper.profiles;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import com.centros_sass.app.dto.workerschedulerecord.WorkerScheduleRecordRequestDTO;
import com.centros_sass.app.dto.workerschedulerecord.WorkerScheduleRecordResponseDTO;
import com.centros_sass.app.dto.workerschedulerecord.WorkerScheduleRecordUpdateDTO;
import com.centros_sass.app.model.profiles.workers.WorkerSchedule;
import com.centros_sass.app.model.profiles.workers.WorkerScheduleRecord;
import com.centros_sass.app.utils.MapperHelper;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    imports = MapperHelper.class
)
public interface WorkerScheduleRecordMapper {

    @Mapping(target = "workerId", source = "worker.id")
    @Mapping(target = "workerFullName", expression = "java(MapperHelper.buildFullName(record.getWorker()))")
    @Mapping(target = "scheduleId", source = "schedule.id")
    @Mapping(target = "hoursWorked", expression = "java(calculateHoursWorked(record))")
    @Mapping(target = "isLate", expression = "java(calculateIsLate(record))")
    @Mapping(target = "createdAt", expression = "java(MapperHelper.formatDateTime(record.getCreatedAt()))")
    @Mapping(target = "updatedAt", expression = "java(MapperHelper.formatDateTime(record.getUpdatedAt()))")
    WorkerScheduleRecordResponseDTO toResponse(WorkerScheduleRecord record);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "isActive", ignore = true)
    @Mapping(target = "worker", ignore = true)
    @Mapping(target = "schedule", ignore = true)
    WorkerScheduleRecord toEntity(WorkerScheduleRecordRequestDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "worker", ignore = true)
    @Mapping(target = "schedule", ignore = true)
    void updateFromDto(WorkerScheduleRecordUpdateDTO dto, @MappingTarget WorkerScheduleRecord entity);

    // Métodos Helpers

    default String calculateHoursWorked(WorkerScheduleRecord record) {
        LocalDateTime clockIn = record.getClockIn();
        LocalDateTime clockOut = record.getClockOut();
        if (clockIn == null || clockOut == null) {
            return null;
        }
        Duration duration = Duration.between(clockIn, clockOut);
        long hours = duration.toHours();
        int minutes = duration.toMinutesPart();
        return hours + "h " + minutes + "m";
    }

    default Boolean calculateIsLate(WorkerScheduleRecord record) {
        WorkerSchedule schedule = record.getSchedule();
        LocalDateTime clockIn = record.getClockIn();
        if (schedule == null || schedule.getStartAt() == null || clockIn == null) {
            return null;
        }
        LocalTime toleranceLimit = schedule.getStartAt().plusMinutes(10);
        return clockIn.toLocalTime().isAfter(toleranceLimit);
    }

}
