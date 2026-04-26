package com.centros_sass.app.mapper.catalogs.people;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import com.centros_sass.app.dto.catalogs.people.RelationshipRequestDTO;
import com.centros_sass.app.dto.catalogs.people.RelationshipResponseDTO;
import com.centros_sass.app.dto.catalogs.people.RelationshipUpdateDTO;
import com.centros_sass.app.model.catalogs.people.Relationship;
import com.centros_sass.app.utils.MapperHelper;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    imports = MapperHelper.class
)
public interface RelationshipMapper {

    @Mapping(target = "createdAt", expression = "java(MapperHelper.formatDateTime(entity.getCreatedAt()))")
    @Mapping(target = "updatedAt", expression = "java(MapperHelper.formatDateTime(entity.getUpdatedAt()))")
    RelationshipResponseDTO toResponse(Relationship entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userContacts", ignore = true)
    Relationship toEntity(RelationshipRequestDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userContacts", ignore = true)
    void updateFromDto(RelationshipUpdateDTO dto, @MappingTarget Relationship entity);

}
