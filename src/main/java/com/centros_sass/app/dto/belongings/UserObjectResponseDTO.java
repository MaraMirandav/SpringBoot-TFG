package com.centros_sass.app.dto.belongings;

public record UserObjectResponseDTO(
    Integer id,
    Integer objectTypeId,
    String objectTypeName,
    Integer itemConditionId,
    String itemConditionName,
    String comment,
    Boolean isActive,
    String createdAt,
    String createdBy,
    String updatedAt,
    String updatedBy
) {}
