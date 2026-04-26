package com.centros_sass.app.dto.belongings;

public record ClothingTypeResponseDTO(
    Integer id,
    String clothingName,
    Boolean isActive,
    String createdAt,
    String createdBy,
    String updatedAt,
    String updatedBy
) {}
