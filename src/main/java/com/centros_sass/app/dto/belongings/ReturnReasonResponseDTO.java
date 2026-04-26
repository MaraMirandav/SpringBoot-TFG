package com.centros_sass.app.dto.belongings;

/**
 * DTO de respuesta para retornar datos de una razón de devolución.
 *
 * @author Equipo de Desarrollo
 * @version 1.0.0
 * @since 1.0.0
 */
public record ReturnReasonResponseDTO(
    Integer id,
    String reason,
    Boolean isActive,
    String createdAt,
    String createdBy,
    String updatedAt,
    String updatedBy
) {}
