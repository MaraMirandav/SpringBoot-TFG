package com.centros_sass.app.dto.belongings;

public record DiaperSizeResponseDTO(
    Integer id,
    String size,
    Boolean isActive,
    String createdAt,
    String createdBy,
    String updatedAt,
    String updatedBy
) {}
