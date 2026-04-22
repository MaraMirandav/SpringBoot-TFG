package com.centros_sass.app.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import com.centros_sass.app.dto.userattendanceday.UserAttendanceDayRequestDTO;
import com.centros_sass.app.dto.userattendanceday.UserAttendanceDayResponseDTO;
import com.centros_sass.app.dto.userattendanceday.UserAttendanceDayUpdateDTO;
import com.centros_sass.app.model.profiles.users.UserAttendanceDay;
import com.centros_sass.app.utils.MapperHelper;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    imports = MapperHelper.class
)
public interface UserAttendanceDayMapper {

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "userFullName", expression = "java(MapperHelper.buildFullName(entity.getUser()))")
    @Mapping(target = "dayId", source = "day.id")
    @Mapping(target = "dayName", expression = "java(buildDayName(entity))")
    @Mapping(target = "createdAt", expression = "java(MapperHelper.formatDateTime(entity.getCreatedAt()))")
    @Mapping(target = "updatedAt", expression = "java(MapperHelper.formatDateTime(entity.getUpdatedAt()))")
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
    default String buildDayName(UserAttendanceDay entity) {
        if (entity == null || entity.getDay() == null) {
            return null;
        }
        return entity.getDay().getDayName();
    }

}
