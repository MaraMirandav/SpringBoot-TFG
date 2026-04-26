package com.centros_sass.app.mapper.catalogs.incidents;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import com.centros_sass.app.dto.catalogs.incidents.CdSignificanceTypeRequestDTO;
import com.centros_sass.app.dto.catalogs.incidents.CdSignificanceTypeResponseDTO;
import com.centros_sass.app.dto.catalogs.incidents.CdSignificanceTypeUpdateDTO;
import com.centros_sass.app.model.catalogs.incidents.center.CdSignificanceType;
import com.centros_sass.app.utils.MapperHelper;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    imports = MapperHelper.class
)
public interface CdSignificanceTypeMapper {

    @Mapping(target = "createdAt", expression = "java(MapperHelper.formatDateTime(entity.getCreatedAt()))")
    @Mapping(target = "updatedAt", expression = "java(MapperHelper.formatDateTime(entity.getUpdatedAt()))")
    CdSignificanceTypeResponseDTO toResponse(CdSignificanceType entity);

    @Mapping(target = "id", ignore = true)
    CdSignificanceType toEntity(CdSignificanceTypeRequestDTO dto);

    @Mapping(target = "id", ignore = true)
    void updateFromDto(CdSignificanceTypeUpdateDTO dto, @MappingTarget CdSignificanceType entity);

}
