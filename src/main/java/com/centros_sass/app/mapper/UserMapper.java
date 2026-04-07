package com.centros_sass.app.mapper;

import java.time.LocalDateTime;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import com.centros_sass.app.dto.user.UserRequestDTO;
import com.centros_sass.app.dto.user.UserResponseDTO;
import com.centros_sass.app.dto.user.UserUpdateDTO;
import com.centros_sass.app.model.catalogs.fixed.people.Dependency;
import com.centros_sass.app.model.catalogs.fixed.people.Sex;
import com.centros_sass.app.model.profiles.users.User;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface UserMapper {

    @Mapping(target = "sexName", expression = "java(extractSexName(user))")
    @Mapping(target = "dependencyName", expression = "java(extractDependencyName(user))")
    @Mapping(target = "createdAt", expression = "java(formatDateTime(user.getCreatedAt()))")
    @Mapping(target = "updatedAt", expression = "java(formatDateTime(user.getUpdatedAt()))")
    UserResponseDTO toResponse(User user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "isActive", ignore = true)
    @Mapping(target = "sex", ignore = true)
    @Mapping(target = "dependency", ignore = true)
    @Mapping(target = "userAdresses", ignore = true)
    @Mapping(target = "userAttendanceDays", ignore = true)
    @Mapping(target = "attendanceRecords", ignore = true)
    @Mapping(target = "userContacts", ignore = true)
    @Mapping(target = "userMedicalInfos", ignore = true)
    @Mapping(target = "userIncidents", ignore = true)
    User toEntity(UserRequestDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "sex", ignore = true)
    @Mapping(target = "dependency", ignore = true)
    @Mapping(target = "userAdresses", ignore = true)
    @Mapping(target = "userAttendanceDays", ignore = true)
    @Mapping(target = "attendanceRecords", ignore = true)
    @Mapping(target = "userContacts", ignore = true)
    @Mapping(target = "userMedicalInfos", ignore = true)
    @Mapping(target = "userIncidents", ignore = true)
    void updateFromDto(UserUpdateDTO dto, @MappingTarget User entity);

    default String extractSexName(User user) {
        Sex sex = user.getSex();
        return sex != null ? sex.getSex() : null;
    }

    default String extractDependencyName(User user) {
        Dependency dependency = user.getDependency();
        return dependency != null ? dependency.getDependencyLevel() : null;
    }

    default String formatDateTime(LocalDateTime dateTime) {
        if (dateTime == null) {
            return null;
        }
        return dateTime.toString();
    }

}
