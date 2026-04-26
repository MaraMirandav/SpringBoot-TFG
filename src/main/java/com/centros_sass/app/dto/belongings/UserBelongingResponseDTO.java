package com.centros_sass.app.dto.belongings;

public record UserBelongingResponseDTO(
    Integer id,
    Integer userId,
    String userFullName,
    Integer workerId,
    String workerFullName,
    Integer userClothingId,
    String clothingTypeName,
    Integer userDiaperId,
    String diaperSizeName,
    String diaperTypeName,
    Integer userObjectId,
    String objectTypeName,
    Boolean isRequest,
    String comment,
    Boolean isActive,
    String createdAt,
    String createdBy,
    String updatedAt,
    String updatedBy
) {}
