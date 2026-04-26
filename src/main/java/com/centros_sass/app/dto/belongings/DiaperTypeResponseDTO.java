package com.centros_sass.app.dto.belongings;

public record DiaperTypeResponseDTO(
    Integer id,
    String type,
    Boolean isActive,
    String createdAt,
    String createdBy,
    String updatedAt,
    String updatedBy
) {}
