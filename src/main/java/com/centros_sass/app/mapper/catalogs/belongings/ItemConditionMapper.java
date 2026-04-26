package com.centros_sass.app.mapper.catalogs.belongings;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import com.centros_sass.app.dto.catalogs.belongings.ItemConditionRequestDTO;
import com.centros_sass.app.dto.catalogs.belongings.ItemConditionResponseDTO;
import com.centros_sass.app.dto.catalogs.belongings.ItemConditionUpdateDTO;
import com.centros_sass.app.model.catalogs.belongings.ItemCondition;
import com.centros_sass.app.utils.MapperHelper;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    imports = MapperHelper.class
)
public interface ItemConditionMapper {

    @Mapping(target = "createdAt", expression = "java(MapperHelper.formatDateTime(condition.getCreatedAt()))")
    @Mapping(target = "updatedAt", expression = "java(MapperHelper.formatDateTime(condition.getUpdatedAt()))")
    ItemConditionResponseDTO toResponse(ItemCondition condition);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userObjects", ignore = true)
    ItemCondition toEntity(ItemConditionRequestDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userObjects", ignore = true)
    void updateFromDto(ItemConditionUpdateDTO dto, @MappingTarget ItemCondition condition);

}
