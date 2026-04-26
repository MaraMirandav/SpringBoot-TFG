package com.centros_sass.app.mapper.catalogs.treatments;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import com.centros_sass.app.dto.catalogs.treatments.AllergyRequestDTO;
import com.centros_sass.app.dto.catalogs.treatments.AllergyResponseDTO;
import com.centros_sass.app.dto.catalogs.treatments.AllergyUpdateDTO;
import com.centros_sass.app.model.catalogs.treatments.Allergy;
import com.centros_sass.app.utils.MapperHelper;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    imports = MapperHelper.class
)
public interface AllergyMapper {

    @Mapping(target = "createdAt", expression = "java(MapperHelper.formatDateTime(entity.getCreatedAt()))")
    @Mapping(target = "updatedAt", expression = "java(MapperHelper.formatDateTime(entity.getUpdatedAt()))")
    AllergyResponseDTO toResponse(Allergy entity);

    @Mapping(target = "id", ignore = true)
    Allergy toEntity(AllergyRequestDTO dto);

    @Mapping(target = "id", ignore = true)
    void updateFromDto(AllergyUpdateDTO dto, @MappingTarget Allergy entity);

}
