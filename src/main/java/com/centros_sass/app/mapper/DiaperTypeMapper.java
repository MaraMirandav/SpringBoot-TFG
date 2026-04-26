package com.centros_sass.app.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import com.centros_sass.app.dto.belongings.DiaperTypeRequestDTO;
import com.centros_sass.app.dto.belongings.DiaperTypeResponseDTO;
import com.centros_sass.app.dto.belongings.DiaperTypeUpdateDTO;
import com.centros_sass.app.model.catalogs.dynamic.belongings.DiaperType;
import com.centros_sass.app.utils.MapperHelper;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    imports = MapperHelper.class
)
public interface DiaperTypeMapper {

    @Mapping(target = "createdAt", expression = "java(MapperHelper.formatDateTime(type.getCreatedAt()))")
    @Mapping(target = "updatedAt", expression = "java(MapperHelper.formatDateTime(type.getUpdatedAt()))")
    DiaperTypeResponseDTO toResponse(DiaperType type);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "isActive", ignore = true)
    @Mapping(target = "userDiapers", ignore = true)
    DiaperType toEntity(DiaperTypeRequestDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userDiapers", ignore = true)
    void updateFromDto(DiaperTypeUpdateDTO dto, @MappingTarget DiaperType type);

}
