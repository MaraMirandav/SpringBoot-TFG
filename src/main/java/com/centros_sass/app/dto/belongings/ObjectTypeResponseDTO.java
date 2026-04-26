package com.centros_sass.app.dto.belongings;

public record ObjectTypeResponseDTO(
    Integer id,
    String objectName,
    Boolean isActive,
    String createdAt,
    String createdBy,
    String updatedAt,
    String updatedBy
) {}
