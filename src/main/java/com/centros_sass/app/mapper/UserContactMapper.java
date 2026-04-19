package com.centros_sass.app.mapper;

import java.time.LocalDateTime;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import com.centros_sass.app.dto.usercontact.UserContactRequestDTO;
import com.centros_sass.app.dto.usercontact.UserContactResponseDTO;
import com.centros_sass.app.dto.usercontact.UserContactUpdateDTO;
import com.centros_sass.app.model.profiles.users.User;
import com.centros_sass.app.model.profiles.users.UserContact;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface UserContactMapper {

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "userFullName", expression = "java(buildUserFullName(entity))")
    @Mapping(target = "relationshipId", source = "contactRelationship.id")
    @Mapping(target = "relationshipName", source = "contactRelationship.relationshipName")
    @Mapping(target = "createdAt", source = "createdAt")
    @Mapping(target = "updatedAt", source = "updatedAt")
    @Mapping(target = "createdBy", source = "createdBy")
    @Mapping(target = "updatedBy", source = "updatedBy")
    UserContactResponseDTO toResponse(UserContact entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "isActive", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "contactRelationship", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    UserContact toEntity(UserContactRequestDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "contactRelationship", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    void updateFromDto(UserContactUpdateDTO dto, @MappingTarget UserContact entity);

    default String buildUserFullName(UserContact entity) {
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

    default String formatDateTime(LocalDateTime dateTime) {
        if (dateTime == null) return null;
        return dateTime.toString();
    }
}