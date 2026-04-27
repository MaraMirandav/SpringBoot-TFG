package com.centros_sass.app.mapper.catalogs.belongings;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import com.centros_sass.app.dto.catalogs.belongings.ObjectTypeRequestDTO;
import com.centros_sass.app.dto.catalogs.belongings.ObjectTypeResponseDTO;
import com.centros_sass.app.dto.catalogs.belongings.ObjectTypeUpdateDTO;
import com.centros_sass.app.model.catalogs.belongings.ObjectType;
import com.centros_sass.app.utils.MapperHelper;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    imports = MapperHelper.class
)
public interface ObjectTypeMapper {

    @Mapping(target = "createdAt", expression = "java(MapperHelper.formatDateTime(type.getCreatedAt()))")
    @Mapping(target = "updatedAt", expression = "java(MapperHelper.formatDateTime(type.getUpdatedAt()))")
    ObjectTypeResponseDTO toResponse(ObjectType type);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userObjects", ignore = true)
    ObjectType toEntity(ObjectTypeRequestDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userObjects", ignore = true)
    void updateFromDto(ObjectTypeUpdateDTO dto, @MappingTarget ObjectType type);

}
