package com.centros_sass.app.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import com.centros_sass.app.dto.workerschedule.WorkerScheduleRequestDTO;
import com.centros_sass.app.dto.workerschedule.WorkerScheduleResponseDTO;
import com.centros_sass.app.dto.workerschedule.WorkerScheduleUpdateDTO;
import com.centros_sass.app.model.profiles.workers.WorkerSchedule;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface WorkerScheduleMapper {

    @Mapping(target = "workerId", source = "worker.id")
    @Mapping(target = "workerFullName", expression = "java(buildWorkerFullName(entity))")
    @Mapping(target = "openDayId", source = "openDay.id")
    @Mapping(target = "dayName", source = "openDay.dayName")
    @Mapping(target = "hasRecord", expression = "java(entity.getRecord() != null)")
    @Mapping(target = "createdAt", expression = "java(formatDateTime(entity.getCreatedAt()))")
    @Mapping(target = "updatedAt", expression = "java(formatDateTime(entity.getUpdatedAt()))")
    WorkerScheduleResponseDTO toResponse(WorkerSchedule entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "isActive", ignore = true)
    @Mapping(target = "worker", ignore = true)
    @Mapping(target = "openDay", ignore = true)
    @Mapping(target = "record", ignore = true)
    WorkerSchedule toEntity(WorkerScheduleRequestDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "worker", ignore = true)
    @Mapping(target = "openDay", ignore = true)
    @Mapping(target = "record", ignore = true)
    void updateFromDto(WorkerScheduleUpdateDTO dto, @MappingTarget WorkerSchedule entity);

    // --- Helpers ---

    default String buildWorkerFullName(WorkerSchedule entity) {
        if (entity.getWorker() == null) {
            return null;
        }
        var w = entity.getWorker();
        StringBuilder sb = new StringBuilder();
        sb.append(w.getFirstName());
        if (w.getSecondName() != null) sb.append(" ").append(w.getSecondName());
        sb.append(" ").append(w.getFirstSurname());
        if (w.getSecondSurname() != null) sb.append(" ").append(w.getSecondSurname());
        return sb.toString();
    }

    default String formatDateTime(java.time.LocalDateTime dateTime) {
        if (dateTime == null) {
            return null;
        }
        return dateTime.toString();
    }

}
