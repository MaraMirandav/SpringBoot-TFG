package com.centros_sass.app.mapper.treatments;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import com.centros_sass.app.dto.treatments.UserIllnessRequestDTO;
import com.centros_sass.app.dto.treatments.UserIllnessResponseDTO;
import com.centros_sass.app.dto.treatments.UserIllnessUpdateDTO;
import com.centros_sass.app.model.treatments.UserIllness;
import com.centros_sass.app.utils.MapperHelper;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    imports = MapperHelper.class
)
public interface UserIllnessMapper {

    @Mapping(target = "userMedicalInfoId", source = "userMedicalInfo.id")
    @Mapping(target = "userFullName", expression = "java(MapperHelper.buildFullName(entity.getUserMedicalInfo() != null ? entity.getUserMedicalInfo().getUser() : null))")
    @Mapping(target = "illnessId", source = "illness.id")
    @Mapping(target = "illnessName", source = "illness.illnessName")
    @Mapping(target = "createdAt", expression = "java(MapperHelper.formatDateTime(entity.getCreatedAt()))")
    @Mapping(target = "updatedAt", expression = "java(MapperHelper.formatDateTime(entity.getUpdatedAt()))")
    UserIllnessResponseDTO toResponse(UserIllness entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "isActive", ignore = true)
    @Mapping(target = "userMedicalInfo", ignore = true)
    @Mapping(target = "illness", ignore = true)
    @Mapping(target = "treatmentDetails", ignore = true)
    UserIllness toEntity(UserIllnessRequestDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userMedicalInfo", ignore = true)
    @Mapping(target = "illness", ignore = true)
    @Mapping(target = "treatmentDetails", ignore = true)
    void updateFromDto(UserIllnessUpdateDTO dto, @MappingTarget UserIllness entity);

}
