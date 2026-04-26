package com.centros_sass.app.mapper.catalogs.treatments;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import com.centros_sass.app.dto.catalogs.treatments.IllnessRequestDTO;
import com.centros_sass.app.dto.catalogs.treatments.IllnessResponseDTO;
import com.centros_sass.app.dto.catalogs.treatments.IllnessUpdateDTO;
import com.centros_sass.app.model.catalogs.treatments.Illness;
import com.centros_sass.app.utils.MapperHelper;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    imports = MapperHelper.class
)
public interface IllnessMapper {

    @Mapping(target = "createdAt", expression = "java(MapperHelper.formatDateTime(entity.getCreatedAt()))")
    @Mapping(target = "updatedAt", expression = "java(MapperHelper.formatDateTime(entity.getUpdatedAt()))")
    IllnessResponseDTO toResponse(Illness entity);

    @Mapping(target = "id", ignore = true)
    Illness toEntity(IllnessRequestDTO dto);

    @Mapping(target = "id", ignore = true)
    void updateFromDto(IllnessUpdateDTO dto, @MappingTarget Illness entity);

}
