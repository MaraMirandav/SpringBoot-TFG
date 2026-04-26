package com.centros_sass.app.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import com.centros_sass.app.dto.belongings.UserClothingRequestDTO;
import com.centros_sass.app.dto.belongings.UserClothingResponseDTO;
import com.centros_sass.app.dto.belongings.UserClothingUpdateDTO;
import com.centros_sass.app.model.belongings.UserClothing;
import com.centros_sass.app.utils.MapperHelper;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    imports = MapperHelper.class
)
public interface UserClothingMapper {

    @Mapping(target = "clothingTypeId", source = "clothingType.id")
    @Mapping(target = "clothingTypeName", source = "clothingType.clothingName")
    @Mapping(target = "returnReasonId", source = "returnReason.id")
    @Mapping(target = "returnReasonName", source = "returnReason.reason")
    @Mapping(target = "receivedAt", expression = "java(MapperHelper.formatDateTime(clothing.getReceivedAt()))")
    @Mapping(target = "returnedAt", expression = "java(MapperHelper.formatDateTime(clothing.getReturnedAt()))")
    @Mapping(target = "createdAt", expression = "java(MapperHelper.formatDateTime(clothing.getCreatedAt()))")
    @Mapping(target = "updatedAt", expression = "java(MapperHelper.formatDateTime(clothing.getUpdatedAt()))")
    UserClothingResponseDTO toResponse(UserClothing clothing);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "isActive", ignore = true)
    @Mapping(target = "clothingType", ignore = true)
    @Mapping(target = "returnReason", ignore = true)
    @Mapping(target = "userBelongings", ignore = true)
    UserClothing toEntity(UserClothingRequestDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "clothingType", ignore = true)
    @Mapping(target = "returnReason", ignore = true)
    @Mapping(target = "userBelongings", ignore = true)
    void updateFromDto(UserClothingUpdateDTO dto, @MappingTarget UserClothing clothing);

}
