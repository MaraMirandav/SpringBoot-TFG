package com.centros_sass.app.dto.bathroom;

import java.time.LocalTime;

/**
 * DTO de respuesta para retornar datos de una tarea de higiene.
 *
 * @author Equipo de Desarrollo
 * @version 1.0.0
 * @since 1.0.0
 */
public record BathroomTaskResponseDTO(
    Integer id,
    String taskName,
    LocalTime estimatedTime,
    Boolean isActive,
    String createdAt,
    String createdBy,
    String updatedAt,
    String updatedBy
) {}
