package com.centros_sass.admin.adapter.in.dto;

import java.time.Instant;

/**
 * AdminUserResponse — DTO de respuesta para un usuario administrador.
 *
 * Se usa en las respuestas API del panel admin.
 * NUNCA expone passwordHash por seguridad.
 */
public record AdminUserResponse(
    Long id,
    String email,
    String fullName,
    String role,
    String status,
    Instant createdAt
) {}