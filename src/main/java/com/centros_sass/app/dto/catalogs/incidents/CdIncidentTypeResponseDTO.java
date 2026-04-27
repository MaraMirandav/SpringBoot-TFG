package com.centros_sass.app.dto.catalogs.incidents;

public record CdIncidentTypeResponseDTO(
    Integer id,
    String incidentName,
    String createdAt,
    String createdBy,
    String updatedAt,
    String updatedBy
) {}
