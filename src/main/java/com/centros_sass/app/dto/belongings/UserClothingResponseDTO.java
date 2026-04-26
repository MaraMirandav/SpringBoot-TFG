package com.centros_sass.app.dto.belongings;

public record UserClothingResponseDTO(
    Integer id,
    Integer clothingTypeId,
    String clothingTypeName,
    Boolean isClean,
    Boolean isReturned,
    String receivedAt,
    String returnedAt,
    Integer returnReasonId,
    String returnReasonName,
    Boolean isActive,
    String createdAt,
    String createdBy,
    String updatedAt,
    String updatedBy
) {}
