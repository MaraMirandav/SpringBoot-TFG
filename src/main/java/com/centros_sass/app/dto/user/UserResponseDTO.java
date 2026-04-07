package com.centros_sass.app.dto.user;

import java.time.LocalDate;

/**
 * DTO de respuesta para retornar datos de un usuario (adulto mayor) al cliente.
 *
 * Utiliza Java Record para inmutabilidad y reducción de boilerplate.
 * Incluye campos de auditoría y nombres de catálogos para facilitar la UI.
 *
 * @author Equipo de Desarrollo
 * @version 1.0.0
 * @since 1.0.0
 */
public record UserResponseDTO(
    Integer id,
    String firstName,
    String secondName,
    String firstSurname,
    String secondSurname,
    String alias,
    String email,
    String phone,
    String cellphone,
    String dni,
    String sexName,
    LocalDate birthDate,
    String dependencyName,
    Boolean isActive,
    String createdAt,
    String createdBy,
    String updatedAt,
    String updatedBy
) {}
