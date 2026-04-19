package com.centros_sass.app.dto.role;

/**
 * DTO de respuesta para retornar datos de un rol al cliente.
 * 
 * Utiliza Java Record para inmutabilidad y reducción de boilerplate.
 * Incluye campos de auditoría para trazabilidad.
 * 
 * @author Equipo de Desarrollo
 * @version 1.0.0
 * @since 1.0.0
 */
public record RoleResponseDTO(
    Integer id,
    String roleName,
    String createdAt,
    String createdBy,
    String updatedAt,
    String updatedBy
) {}