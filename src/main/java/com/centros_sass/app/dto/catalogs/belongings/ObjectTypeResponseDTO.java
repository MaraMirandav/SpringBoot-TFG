package com.centros_sass.app.dto.catalogs.belongings;

public record ObjectTypeResponseDTO(
    Integer id,
    String objectName,
    String createdAt,
    String createdBy,
    String updatedAt,
    String updatedBy
) {}
