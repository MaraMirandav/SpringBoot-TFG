package com.centros_sass.app.dto.catalogs.belongings;

public record ClothingTypeResponseDTO(
    Integer id,
    String clothingName,
    String createdAt,
    String createdBy,
    String updatedAt,
    String updatedBy
) {}
