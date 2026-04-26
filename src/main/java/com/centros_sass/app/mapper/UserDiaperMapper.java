package com.centros_sass.app.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import com.centros_sass.app.dto.belongings.UserDiaperRequestDTO;
import com.centros_sass.app.dto.belongings.UserDiaperResponseDTO;
import com.centros_sass.app.dto.belongings.UserDiaperUpdateDTO;
import com.centros_sass.app.model.belongings.UserDiaper;
import com.centros_sass.app.utils.MapperHelper;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    imports = MapperHelper.class
)
public interface UserDiaperMapper {

    @Mapping(target = "diaperSizeId", source = "diaperSize.id")
    @Mapping(target = "diaperSizeName", source = "diaperSize.size")
    @Mapping(target = "diaperTypeId", source = "diaperType.id")
    @Mapping(target = "diaperTypeName", source = "diaperType.type")
    @Mapping(target = "createdAt", expression = "java(MapperHelper.formatDateTime(diaper.getCreatedAt()))")
    @Mapping(target = "updatedAt", expression = "java(MapperHelper.formatDateTime(diaper.getUpdatedAt()))")
    UserDiaperResponseDTO toResponse(UserDiaper diaper);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "isActive", ignore = true)
    @Mapping(target = "diaperSize", ignore = true)
    @Mapping(target = "diaperType", ignore = true)
    @Mapping(target = "userBelongings", ignore = true)
    UserDiaper toEntity(UserDiaperRequestDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "diaperSize", ignore = true)
    @Mapping(target = "diaperType", ignore = true)
    @Mapping(target = "userBelongings", ignore = true)
    void updateFromDto(UserDiaperUpdateDTO dto, @MappingTarget UserDiaper diaper);

}
