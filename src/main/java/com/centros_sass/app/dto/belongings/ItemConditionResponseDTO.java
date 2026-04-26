package com.centros_sass.app.dto.belongings;

public record ItemConditionResponseDTO(
    Integer id,
    String conditionName,
    Boolean isActive,
    String createdAt,
    String createdBy,
    String updatedAt,
    String updatedBy
) {}
