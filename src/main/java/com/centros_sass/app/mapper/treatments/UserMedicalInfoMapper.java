package com.centros_sass.app.mapper.treatments;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import com.centros_sass.app.dto.treatments.UserMedicalInfoRequestDTO;
import com.centros_sass.app.dto.treatments.UserMedicalInfoResponseDTO;
import com.centros_sass.app.dto.treatments.UserMedicalInfoUpdateDTO;
import com.centros_sass.app.model.treatments.UserMedicalInfo;
import com.centros_sass.app.utils.MapperHelper;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    imports = MapperHelper.class
)
public interface UserMedicalInfoMapper {

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "userFullName", expression = "java(MapperHelper.buildFullName(entity.getUser()))")
    @Mapping(target = "workerId", source = "worker.id")
    @Mapping(target = "workerFullName", expression = "java(MapperHelper.buildFullName(entity.getWorker()))")
    @Mapping(target = "createdAt", expression = "java(MapperHelper.formatDateTime(entity.getCreatedAt()))")
    @Mapping(target = "updatedAt", expression = "java(MapperHelper.formatDateTime(entity.getUpdatedAt()))")
    UserMedicalInfoResponseDTO toResponse(UserMedicalInfo entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "isActive", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "worker", ignore = true)
    @Mapping(target = "userAllergies", ignore = true)
    @Mapping(target = "userIllnesses", ignore = true)
    UserMedicalInfo toEntity(UserMedicalInfoRequestDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "worker", ignore = true)
    @Mapping(target = "userAllergies", ignore = true)
    @Mapping(target = "userIllnesses", ignore = true)
    void updateFromDto(UserMedicalInfoUpdateDTO dto, @MappingTarget UserMedicalInfo entity);

}
