package com.centros_sass.app.dto.bathroom;

import java.time.LocalTime;

/**
 * DTO de respuesta para retornar datos de un turno de baño.
 *
 * @author Equipo de Desarrollo
 * @version 1.0.0
 * @since 1.0.0
 */
public record BathroomTurnResponseDTO(
    Integer id,
    String turnName,
    LocalTime startAt,
    LocalTime endAt,
    String createdAt,
    String createdBy,
    String updatedAt,
    String updatedBy
) {}
