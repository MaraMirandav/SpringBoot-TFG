package com.centros_sass.app.dto.bathroom;

/**
 * DTO de respuesta para retornar datos de un registro de higiene.
 *
 * @author Equipo de Desarrollo
 * @version 1.0.0
 * @since 1.0.0
 */
public record BathroomScheduleResponseDTO(
    Integer id,
    Integer userId,
    String userFullName,
    Integer bathroomTurnId,
    String turnName,
    Integer bathroomTaskId,
    String taskName,
    Boolean isActive,
    String createdAt,
    String createdBy,
    String updatedAt,
    String updatedBy
) {}
