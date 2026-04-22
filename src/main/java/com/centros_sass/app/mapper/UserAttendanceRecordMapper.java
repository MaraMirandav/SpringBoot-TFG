package com.centros_sass.app.mapper;

import java.time.LocalDateTime;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import com.centros_sass.app.dto.userattendancerecord.UserAttendanceRecordRequestDTO;
import com.centros_sass.app.dto.userattendancerecord.UserAttendanceRecordResponseDTO;
import com.centros_sass.app.model.profiles.users.User;
import com.centros_sass.app.model.profiles.users.UserAttendanceRecord;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface UserAttendanceRecordMapper {

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "userFullName", expression = "java(buildUserFullName(entity))")
    @Mapping(target = "attendanceDayId", source = "attendanceDay.id")
    @Mapping(target = "dayName", expression = "java(buildDayName(entity))")
    @Mapping(target = "startAt", expression = "java(entity.getAttendanceDay() != null ? entity.getAttendanceDay().getStartAt() : null)")
    @Mapping(target = "endAt", expression = "java(entity.getAttendanceDay() != null ? entity.getAttendanceDay().getEndAt() : null)")
    @Mapping(target = "createdAt", expression = "java(formatDateTime(entity.getCreatedAt()))")
    @Mapping(target = "updatedAt", expression = "java(formatDateTime(entity.getUpdatedAt()))")
    UserAttendanceRecordResponseDTO toResponse(UserAttendanceRecord entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "attendanceDay", ignore = true)
    UserAttendanceRecord toEntity(UserAttendanceRecordRequestDTO dto);

    // Métodos Helper

    default String buildUserFullName(UserAttendanceRecord entity) {
        if (entity == null || entity.getUser() == null) {
            return null;
        }
        User user = entity.getUser();
        StringBuilder fullName = new StringBuilder();
        fullName.append(user.getFirstName());
        if (user.getSecondName() != null && !user.getSecondName().isEmpty()) {
            fullName.append(" ").append(user.getSecondName());
        }
        fullName.append(" ").append(user.getFirstSurname());
        if (user.getSecondSurname() != null && !user.getSecondSurname().isEmpty()) {
            fullName.append(" ").append(user.getSecondSurname());
        }
        return fullName.toString();
    }

    default String buildDayName(UserAttendanceRecord entity) {
        if (entity == null || entity.getAttendanceDay() == null || entity.getAttendanceDay().getDay() == null) {
            return null;
        }
        return entity.getAttendanceDay().getDay().getDayName();
    }

    default String formatDateTime(LocalDateTime dateTime) {
        if (dateTime == null) {
            return null;
        }
        return dateTime.toString();
    }

}
