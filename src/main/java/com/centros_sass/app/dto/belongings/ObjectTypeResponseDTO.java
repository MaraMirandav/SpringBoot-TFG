package com.centros_sass.app.dto.belongings;

/**
 * DTO de respuesta para retornar datos de un tipo de objeto.
 *
 * @author Equipo de Desarrollo
 * @version 1.0.0
 * @since 1.0.0
 */
public record ObjectTypeResponseDTO(
    Integer id,
    String objectName,
    Boolean isActive,
    String createdAt,
    String createdBy,
    String updatedAt,
    String updatedBy
) {}
