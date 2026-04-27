package com.centros_sass.app.mapper.catalogs.people;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import com.centros_sass.app.dto.catalogs.people.SexRequestDTO;
import com.centros_sass.app.dto.catalogs.people.SexResponseDTO;
import com.centros_sass.app.dto.catalogs.people.SexUpdateDTO;
import com.centros_sass.app.model.catalogs.people.Sex;
import com.centros_sass.app.utils.MapperHelper;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    imports = MapperHelper.class
)
public interface SexMapper {

    @Mapping(target = "createdAt", expression = "java(MapperHelper.formatDateTime(entity.getCreatedAt()))")
    @Mapping(target = "updatedAt", expression = "java(MapperHelper.formatDateTime(entity.getUpdatedAt()))")
    SexResponseDTO toResponse(Sex entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "users", ignore = true)
    Sex toEntity(SexRequestDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "users", ignore = true)
    void updateFromDto(SexUpdateDTO dto, @MappingTarget Sex entity);

}
