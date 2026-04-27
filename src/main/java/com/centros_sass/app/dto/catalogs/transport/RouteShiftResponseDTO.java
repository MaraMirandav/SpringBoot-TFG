package com.centros_sass.app.dto.catalogs.transport;

public record RouteShiftResponseDTO(
    Integer id,
    String routeName,
    String createdAt,
    String createdBy,
    String updatedAt,
    String updatedBy
) {}
