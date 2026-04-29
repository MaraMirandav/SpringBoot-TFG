package com.centros_sass.admin.adapter.in.mapper;

import com.centros_sass.admin.adapter.in.dto.CreatePlanRequest;
import com.centros_sass.admin.adapter.in.dto.PlanResponse;
import com.centros_sass.admin.adapter.in.dto.UpdatePlanRequest;
import com.centros_sass.admin.domain.model.PlanEntity;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface PlanMapper {

    PlanEntity toEntity(CreatePlanRequest request);

    PlanEntity toEntity(UpdatePlanRequest request);

    PlanResponse toResponse(PlanEntity entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateFromRequest(UpdatePlanRequest request, @MappingTarget PlanEntity entity);
}