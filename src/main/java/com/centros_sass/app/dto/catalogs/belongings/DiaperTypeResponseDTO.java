package com.centros_sass.app.dto.catalogs.belongings;

public record DiaperTypeResponseDTO(
    Integer id,
    String type,
    String createdAt,
    String createdBy,
    String updatedAt,
    String updatedBy
) {}
