package com.centros_sass.admin.adapter.in.dto;

import java.time.Instant;

/**
 * TenantResponse — DTO de respuesta para un tenant.
 *
 * Se usa en las respuestas API del panel admin.
 * Nunca expone datos sensibles como passwordHash.
 */
public record TenantResponse(
    Long id,
    String name,
    String slug,
    String adminEmail,
    String status,
    String planSlug,
    Instant createdAt
) {}