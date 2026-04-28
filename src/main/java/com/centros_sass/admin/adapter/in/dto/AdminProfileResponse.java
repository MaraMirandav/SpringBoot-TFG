package com.centros_sass.admin.adapter.in.dto;

import java.time.Instant;

/**
 * AdminProfileResponse — DTO de respuesta para el perfil del admin logueado.
 *
 * Se usa en el endpoint GET /api/v1/admin/profile.
 * Expone los datos públicos del perfil: id, email, fullName, role, createdAt.
 * NUNCA expone passwordHash por seguridad.
 *
 * @author SaasCon
 * @since 1.0
 */
public record AdminProfileResponse(
    /**
     * ID único del administrador en la base de datos.
     * Generado automáticamente por la BD (auto-increment).
     */
    Long id,

    /**
     * Email corporativo del admin — usado como identificador de login.
     * Es único en toda la tabla admin_users.
     * En el JWT aparece en el claim "sub" (subject).
     */
    String email,

    /**
     * Nombre completo del administrador para mostrar en la UI.
     * Máximo 150 caracteres.
     */
    String fullName,

    /**
     * Rol del administrador en el sistema.
     * Valores posibles: SUPER_ADMIN, ADMIN, SUPPORT.
     * Determina los permisos dentro del panel de administración.
     */
    String role,

    /**
     * Timestamp de creación del registro.
     * Usado para mostrar "Miembro desde..." en el perfil.
     * Generado automáticamente por @CreatedDate de JPA.
     */
    Instant createdAt
) {}