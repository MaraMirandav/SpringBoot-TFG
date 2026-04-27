package com.centros_sass.app.mapper.catalogs.address;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import com.centros_sass.app.dto.catalogs.address.RegionRequestDTO;
import com.centros_sass.app.dto.catalogs.address.RegionResponseDTO;
import com.centros_sass.app.dto.catalogs.address.RegionUpdateDTO;
import com.centros_sass.app.model.catalogs.address.Region;
import com.centros_sass.app.utils.MapperHelper;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    imports = MapperHelper.class
)
public interface RegionMapper {

    @Mapping(target = "createdAt", expression = "java(MapperHelper.formatDateTime(entity.getCreatedAt()))")
    @Mapping(target = "updatedAt", expression = "java(MapperHelper.formatDateTime(entity.getUpdatedAt()))")
    RegionResponseDTO toResponse(Region entity);

    @Mapping(target = "id", ignore = true)
    Region toEntity(RegionRequestDTO dto);

    @Mapping(target = "id", ignore = true)
    void updateFromDto(RegionUpdateDTO dto, @MappingTarget Region entity);

}
