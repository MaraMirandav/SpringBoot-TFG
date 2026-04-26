package com.centros_sass.app.dto.catalogs.belongings;

public record ItemConditionResponseDTO(
    Integer id,
    String conditionName,
    String createdAt,
    String createdBy,
    String updatedAt,
    String updatedBy
) {}
