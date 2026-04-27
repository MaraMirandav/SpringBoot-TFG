package com.centros_sass.app.dto.incidents;

public record CenterIncidentResponseDTO(

    Integer id,
    Integer reportedById,
    String reportedByFullName,
    String comment,
    Integer incidentStatusId,
    String incidentStatusName,
    Integer cdIncidentTypeId,
    String cdIncidentTypeName,
    Integer cdSignificanceTypeId,
    String cdSignificanceTypeName,
    Integer commentCount,
    Boolean isActive,
    String createdAt,
    String createdBy,
    String updatedAt,
    String updatedBy

) {}
