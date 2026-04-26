package com.centros_sass.app.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import com.centros_sass.app.dto.belongings.DiaperSizeRequestDTO;
import com.centros_sass.app.dto.belongings.DiaperSizeResponseDTO;
import com.centros_sass.app.dto.belongings.DiaperSizeUpdateDTO;
import com.centros_sass.app.model.catalogs.dynamic.belongings.DiaperSize;
import com.centros_sass.app.utils.MapperHelper;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    imports = MapperHelper.class
)
public interface DiaperSizeMapper {

    @Mapping(target = "createdAt", expression = "java(MapperHelper.formatDateTime(size.getCreatedAt()))")
    @Mapping(target = "updatedAt", expression = "java(MapperHelper.formatDateTime(size.getUpdatedAt()))")
    DiaperSizeResponseDTO toResponse(DiaperSize size);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "isActive", ignore = true)
    @Mapping(target = "userDiapers", ignore = true)
    DiaperSize toEntity(DiaperSizeRequestDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userDiapers", ignore = true)
    void updateFromDto(DiaperSizeUpdateDTO dto, @MappingTarget DiaperSize size);

}
