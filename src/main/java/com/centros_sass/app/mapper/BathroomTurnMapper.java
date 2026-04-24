package com.centros_sass.app.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import com.centros_sass.app.dto.bathroom.BathroomTurnRequestDTO;
import com.centros_sass.app.dto.bathroom.BathroomTurnResponseDTO;
import com.centros_sass.app.dto.bathroom.BathroomTurnUpdateDTO;
import com.centros_sass.app.model.bathroom.BathroomTurn;
import com.centros_sass.app.utils.MapperHelper;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    imports = MapperHelper.class
)
public interface BathroomTurnMapper {

    @Mapping(target = "createdAt", expression = "java(MapperHelper.formatDateTime(turn.getCreatedAt()))")
    @Mapping(target = "updatedAt", expression = "java(MapperHelper.formatDateTime(turn.getUpdatedAt()))")
    BathroomTurnResponseDTO toResponse(BathroomTurn turn);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "bathroomSchedules", ignore = true)
    BathroomTurn toEntity(BathroomTurnRequestDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "bathroomSchedules", ignore = true)
    void updateFromDto(BathroomTurnUpdateDTO dto, @MappingTarget BathroomTurn turn);

}
