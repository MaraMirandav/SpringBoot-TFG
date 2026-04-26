package com.centros_sass.app.dto.catalogs.belongings;

public record ReturnReasonResponseDTO(
    Integer id,
    String reason,
    String createdAt,
    String createdBy,
    String updatedAt,
    String updatedBy
) {}
