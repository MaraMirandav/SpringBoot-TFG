package com.centros_sass.app.dto.catalogs.incidents;

public record IncidentStatusResponseDTO(
    Integer id,
    String statusName,
    String createdAt,
    String createdBy,
    String updatedAt,
    String updatedBy
) {}
