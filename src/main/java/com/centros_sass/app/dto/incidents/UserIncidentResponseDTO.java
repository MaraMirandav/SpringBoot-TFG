package com.centros_sass.app.dto.incidents;

public record UserIncidentResponseDTO(

    Integer id,
    Integer userId,
    String userFullName,
    Integer reportedById,
    String reportedByFullName,
    String comment,
    Integer incidentStatusId,
    String incidentStatusName,
    Integer userIncidentTypeId,
    String userIncidentTypeName,
    Integer userSignificanceTypeId,
    String userSignificanceTypeName,
    Integer commentCount,
    Boolean isActive,
    String createdAt,
    String createdBy,
    String updatedAt,
    String updatedBy

) {}
