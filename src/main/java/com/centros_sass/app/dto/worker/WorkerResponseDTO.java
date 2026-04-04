package com.centros_sass.app.dto.worker;

import java.util.Set;

/**
 * DTO de respuesta para retornar datos de un trabajador al cliente.
 *
 * Utiliza Java Record para inmutabilidad y reducciÃ³n de boilerplate.
 * No incluye datos sensibles (password).
 *
 * @author Equipo de Desarrollo
 * @version 1.0.0
 * @since 1.0.0
 */
public record WorkerResponseDTO(
    Integer id,
    String firstName,
    String secondName,
    String firstSurname,
    String secondSurname,
    String dni,
    String mainPhone,
    String secondPhone,
    String email,
    Boolean isActive,
    Set<Integer> roleIds,
    Integer scheduleCount,
    String createdAt,
    String createdBy,
    String updatedAt,
    String updatedBy
) {}
