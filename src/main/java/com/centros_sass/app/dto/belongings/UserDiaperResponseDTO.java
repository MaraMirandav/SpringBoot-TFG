package com.centros_sass.app.dto.belongings;

public record UserDiaperResponseDTO(
    Integer id,
    Integer diaperSizeId,
    String diaperSizeName,
    Integer diaperTypeId,
    String diaperTypeName,
    Integer quantity,
    Boolean isActive,
    String createdAt,
    String createdBy,
    String updatedAt,
    String updatedBy
) {}
