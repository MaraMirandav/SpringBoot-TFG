package com.centros_sass.app.dto.belongings;

public record ReturnReasonResponseDTO(
    Integer id,
    String reason,
    Boolean isActive,
    String createdAt,
    String createdBy,
    String updatedAt,
    String updatedBy
) {}
