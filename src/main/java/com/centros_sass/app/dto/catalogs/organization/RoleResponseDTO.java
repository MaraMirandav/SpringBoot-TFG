package com.centros_sass.app.dto.catalogs.organization;

public record RoleResponseDTO(
    Integer id,
    String roleName,
    String createdAt,
    String createdBy,
    String updatedAt,
    String updatedBy
) {}