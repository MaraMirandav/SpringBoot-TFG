package com.centros_sass.app.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import com.centros_sass.app.dto.userattendancerecord.UserAttendanceRecordRequestDTO;
import com.centros_sass.app.dto.userattendancerecord.UserAttendanceRecordResponseDTO;
import com.centros_sass.app.model.profiles.users.UserAttendanceRecord;
import com.centros_sass.app.utils.MapperHelper;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    imports = MapperHelper.class
)
public interface UserAttendanceRecordMapper {

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "userFullName", expression = "java(MapperHelper.buildFullName(entity.getUser()))")
    @Mapping(target = "attendanceDayId", source = "attendanceDay.id")
    @Mapping(target = "dayName", expression = "java(buildDayName(entity))")
    @Mapping(target = "startAt", expression = "java(entity.getAttendanceDay() != null ? entity.getAttendanceDay().getStartAt() : null)")
    @Mapping(target = "endAt", expression = "java(entity.getAttendanceDay() != null ? entity.getAttendanceDay().getEndAt() : null)")
    @Mapping(target = "createdAt", expression = "java(MapperHelper.formatDateTime(entity.getCreatedAt()))")
    @Mapping(target = "updatedAt", expression = "java(MapperHelper.formatDateTime(entity.getUpdatedAt()))")
    UserAttendanceRecordResponseDTO toResponse(UserAttendanceRecord entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "attendanceDay", ignore = true)
    UserAttendanceRecord toEntity(UserAttendanceRecordRequestDTO dto);

    // Métodos Helper

    default String buildDayName(UserAttendanceRecord entity) {
        if (entity == null || entity.getAttendanceDay() == null || entity.getAttendanceDay().getDay() == null) {
            return null;
        }
        return entity.getAttendanceDay().getDay().getDayName();
    }

}
