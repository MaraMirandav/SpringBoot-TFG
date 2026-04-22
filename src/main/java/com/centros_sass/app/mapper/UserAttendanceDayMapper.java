package com.centros_sass.app.mapper;

import java.time.LocalDateTime;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import com.centros_sass.app.dto.userattendanceday.UserAttendanceDayRequestDTO;
import com.centros_sass.app.dto.userattendanceday.UserAttendanceDayResponseDTO;
import com.centros_sass.app.dto.userattendanceday.UserAttendanceDayUpdateDTO;
import com.centros_sass.app.model.profiles.users.User;
import com.centros_sass.app.model.profiles.users.UserAttendanceDay;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface UserAttendanceDayMapper {

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "userFullName", expression = "java(buildUserFullName(entity))")
    @Mapping(target = "dayId", source = "day.id")
    @Mapping(target = "dayName", expression = "java(buildDayName(entity))")
    @Mapping(target = "createdAt", expression = "java(formatDateTime(entity.getCreatedAt()))")
    @Mapping(target = "updatedAt", expression = "java(formatDateTime(entity.getUpdatedAt()))")
    UserAttendanceDayResponseDTO toResponse(UserAttendanceDay entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "isActive", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "day", ignore = true)
    UserAttendanceDay toEntity(UserAttendanceDayRequestDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "day", ignore = true)
    void updateFromDto(UserAttendanceDayUpdateDTO dto, @MappingTarget UserAttendanceDay entity);

    // Métodos Helper
    default String buildUserFullName(UserAttendanceDay entity) {
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

    default String buildDayName(UserAttendanceDay entity) {
        if (entity == null || entity.getDay() == null) {
            return null;
        }
        return entity.getDay().getDayName();
    }

    default String formatDateTime(LocalDateTime dateTime) {
        if (dateTime == null) {
            return null;
        }
        return dateTime.toString();
    }

}
