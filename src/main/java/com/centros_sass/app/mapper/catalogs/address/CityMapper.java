package com.centros_sass.app.mapper.catalogs.address;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import com.centros_sass.app.dto.catalogs.address.CityRequestDTO;
import com.centros_sass.app.dto.catalogs.address.CityResponseDTO;
import com.centros_sass.app.dto.catalogs.address.CityUpdateDTO;
import com.centros_sass.app.model.catalogs.address.City;
import com.centros_sass.app.utils.MapperHelper;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    imports = MapperHelper.class
)
public interface CityMapper {

    @Mapping(target = "createdAt", expression = "java(MapperHelper.formatDateTime(entity.getCreatedAt()))")
    @Mapping(target = "updatedAt", expression = "java(MapperHelper.formatDateTime(entity.getUpdatedAt()))")
    CityResponseDTO toResponse(City entity);

    @Mapping(target = "id", ignore = true)
    City toEntity(CityRequestDTO dto);

    @Mapping(target = "id", ignore = true)
    void updateFromDto(CityUpdateDTO dto, @MappingTarget City entity);

}
