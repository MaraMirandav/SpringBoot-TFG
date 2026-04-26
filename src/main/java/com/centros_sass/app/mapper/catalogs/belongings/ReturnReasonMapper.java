package com.centros_sass.app.mapper.catalogs.belongings;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import com.centros_sass.app.dto.catalogs.belongings.ReturnReasonRequestDTO;
import com.centros_sass.app.dto.catalogs.belongings.ReturnReasonResponseDTO;
import com.centros_sass.app.dto.catalogs.belongings.ReturnReasonUpdateDTO;
import com.centros_sass.app.model.catalogs.belongings.ReturnReason;
import com.centros_sass.app.utils.MapperHelper;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    imports = MapperHelper.class
)
public interface ReturnReasonMapper {

    @Mapping(target = "createdAt", expression = "java(MapperHelper.formatDateTime(reason.getCreatedAt()))")
    @Mapping(target = "updatedAt", expression = "java(MapperHelper.formatDateTime(reason.getUpdatedAt()))")
    ReturnReasonResponseDTO toResponse(ReturnReason reason);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userClothings", ignore = true)
    ReturnReason toEntity(ReturnReasonRequestDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userClothings", ignore = true)
    void updateFromDto(ReturnReasonUpdateDTO dto, @MappingTarget ReturnReason reason);

}
