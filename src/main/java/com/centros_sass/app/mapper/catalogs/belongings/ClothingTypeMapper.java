package com.centros_sass.app.mapper.catalogs.belongings;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import com.centros_sass.app.dto.catalogs.belongings.ClothingTypeRequestDTO;
import com.centros_sass.app.dto.catalogs.belongings.ClothingTypeResponseDTO;
import com.centros_sass.app.dto.catalogs.belongings.ClothingTypeUpdateDTO;
import com.centros_sass.app.model.catalogs.belongings.ClothingType;
import com.centros_sass.app.utils.MapperHelper;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    imports = MapperHelper.class
)
public interface ClothingTypeMapper {

    @Mapping(target = "createdAt", expression = "java(MapperHelper.formatDateTime(type.getCreatedAt()))")
    @Mapping(target = "updatedAt", expression = "java(MapperHelper.formatDateTime(type.getUpdatedAt()))")
    ClothingTypeResponseDTO toResponse(ClothingType type);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userClothings", ignore = true)
    ClothingType toEntity(ClothingTypeRequestDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userClothings", ignore = true)
    void updateFromDto(ClothingTypeUpdateDTO dto, @MappingTarget ClothingType type);

}
