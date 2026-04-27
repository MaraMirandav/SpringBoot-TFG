package com.centros_sass.app.mapper.catalogs.incidents;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import com.centros_sass.app.dto.catalogs.incidents.UserSignificanceTypeRequestDTO;
import com.centros_sass.app.dto.catalogs.incidents.UserSignificanceTypeResponseDTO;
import com.centros_sass.app.dto.catalogs.incidents.UserSignificanceTypeUpdateDTO;
import com.centros_sass.app.model.catalogs.incidents.user.UserSignificanceType;
import com.centros_sass.app.utils.MapperHelper;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    imports = MapperHelper.class
)
public interface UserSignificanceTypeMapper {

    @Mapping(target = "createdAt", expression = "java(MapperHelper.formatDateTime(entity.getCreatedAt()))")
    @Mapping(target = "updatedAt", expression = "java(MapperHelper.formatDateTime(entity.getUpdatedAt()))")
    UserSignificanceTypeResponseDTO toResponse(UserSignificanceType entity);

    @Mapping(target = "id", ignore = true)
    UserSignificanceType toEntity(UserSignificanceTypeRequestDTO dto);

    @Mapping(target = "id", ignore = true)
    void updateFromDto(UserSignificanceTypeUpdateDTO dto, @MappingTarget UserSignificanceType entity);

}
