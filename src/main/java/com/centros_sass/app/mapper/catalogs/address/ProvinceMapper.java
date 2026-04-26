package com.centros_sass.app.mapper.catalogs.address;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import com.centros_sass.app.dto.catalogs.address.ProvinceRequestDTO;
import com.centros_sass.app.dto.catalogs.address.ProvinceResponseDTO;
import com.centros_sass.app.dto.catalogs.address.ProvinceUpdateDTO;
import com.centros_sass.app.model.catalogs.address.Province;
import com.centros_sass.app.utils.MapperHelper;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    imports = MapperHelper.class
)
public interface ProvinceMapper {

    @Mapping(target = "createdAt", expression = "java(MapperHelper.formatDateTime(entity.getCreatedAt()))")
    @Mapping(target = "updatedAt", expression = "java(MapperHelper.formatDateTime(entity.getUpdatedAt()))")
    ProvinceResponseDTO toResponse(Province entity);

    @Mapping(target = "id", ignore = true)
    Province toEntity(ProvinceRequestDTO dto);

    @Mapping(target = "id", ignore = true)
    void updateFromDto(ProvinceUpdateDTO dto, @MappingTarget Province entity);

}
