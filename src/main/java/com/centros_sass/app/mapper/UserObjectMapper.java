package com.centros_sass.app.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import com.centros_sass.app.dto.belongings.UserObjectRequestDTO;
import com.centros_sass.app.dto.belongings.UserObjectResponseDTO;
import com.centros_sass.app.dto.belongings.UserObjectUpdateDTO;
import com.centros_sass.app.model.belongings.UserObject;
import com.centros_sass.app.utils.MapperHelper;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    imports = MapperHelper.class
)
public interface UserObjectMapper {

    @Mapping(target = "objectTypeId", source = "objectType.id")
    @Mapping(target = "objectTypeName", source = "objectType.objectName")
    @Mapping(target = "itemConditionId", source = "itemCondition.id")
    @Mapping(target = "itemConditionName", source = "itemCondition.conditionName")
    @Mapping(target = "createdAt", expression = "java(MapperHelper.formatDateTime(obj.getCreatedAt()))")
    @Mapping(target = "updatedAt", expression = "java(MapperHelper.formatDateTime(obj.getUpdatedAt()))")
    UserObjectResponseDTO toResponse(UserObject obj);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "isActive", ignore = true)
    @Mapping(target = "objectType", ignore = true)
    @Mapping(target = "itemCondition", ignore = true)
    @Mapping(target = "userBelongings", ignore = true)
    UserObject toEntity(UserObjectRequestDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "objectType", ignore = true)
    @Mapping(target = "itemCondition", ignore = true)
    @Mapping(target = "userBelongings", ignore = true)
    void updateFromDto(UserObjectUpdateDTO dto, @MappingTarget UserObject obj);

}
