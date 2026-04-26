package com.centros_sass.app.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import com.centros_sass.app.dto.people.DependencyRequestDTO;
import com.centros_sass.app.dto.people.DependencyResponseDTO;
import com.centros_sass.app.dto.people.DependencyUpdateDTO;
import com.centros_sass.app.model.catalogs.dynamic.people.Dependency;
import com.centros_sass.app.utils.MapperHelper;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    imports = MapperHelper.class
)
public interface DependencyMapper {

    @Mapping(target = "createdAt", expression = "java(MapperHelper.formatDateTime(entity.getCreatedAt()))")
    @Mapping(target = "updatedAt", expression = "java(MapperHelper.formatDateTime(entity.getUpdatedAt()))")
    DependencyResponseDTO toResponse(Dependency entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "users", ignore = true)
    Dependency toEntity(DependencyRequestDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "users", ignore = true)
    void updateFromDto(DependencyUpdateDTO dto, @MappingTarget Dependency entity);

}
