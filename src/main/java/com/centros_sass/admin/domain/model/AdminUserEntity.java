package com.centros_sass.admin.domain.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

/**
 * AdminUserEntity — empleado interno de SaasCon con acceso al panel de administración.
 *
 * Vive en schema "public" (tabla admin_users) y es COMPLETAMENTE DISTINTA
 * de los usuarios de tenant (que viven en tenant_*.users).
 *
 * Distinción crítica en SaaS multitenant:
 *   admin_users (public)    → empleados de quien VENDE el SaaS (nosotros, SaasCon)
 *   users (tenant_acme.*)   → empleados de quien COMPRA el SaaS (el cliente)
 *
 * La contraseña se guarda como hash BCrypt (work factor 12).
 * NUNCA guardar contraseñas en texto plano — es violación de seguridad.
 *
 * Mapea a: V4__create_admin_users_table.sql
 */
@Entity
@Table(name = "admin_users", schema = "public")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminUserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Email corporativo del empleado — usado como login al panel.
     * UNIQUE en toda la tabla — no dos admins con el mismo email.
     */
    @Column(name = "email", nullable = false, unique = true, length = 255)
    private String email;

    /**
     * Hash BCrypt de la contraseña (work factor 12).
     * passwordEncoder.encode(rawPassword) en el use case de creación.
     * NUNCA devolver este campo en responses de API.
     */
    @Column(name = "password_hash", nullable = false, length = 255)
    private String passwordHash;

    // Nombre completo para mostrar en la UI del panel admin
    @Column(name = "full_name", nullable = false, length = 150)
    private String fullName;

    /**
     * Rol del administrador — define sus permisos en el panel.
     * EnumType.STRING guarda "SUPER_ADMIN", "ADMIN" o "SUPPORT" en la BD.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false, length = 20)
    private AdminUserRole role;

    /**
     * Estado de la cuenta — ACTIVE o INACTIVE.
     * INACTIVE bloquea el acceso sin borrar el historial de auditoría.
     * Si borramos el registro, perdemos quién hizo qué en el pasado.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private AdminUserStatus status;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;
}

// ─── ¿QUÉ APRENDER DE ESTA CLASE? ────────────────────────────────────────────
// 1. NUNCA exponer en API responses: tiene passwordHash — usar AdminUserMapper
//    que mapee a AdminUserResponse (DTO sin el campo password)
// 2. INACTIVE vs DELETE: conservar el registro garantiza que audit_log
//    mantenga la referencia "quién hizo qué" → integridad del historial
// 3. Dos enums distintos: AdminUserRole (qué puede hacer) y AdminUserStatus (activo/inactivo)
//    Son conceptos independientes — no mezclarlos en un solo campo
// 4. schema = "public" obligatorio igual que TenantEntity — admin_users no es por-tenant
// ─────────────────────────────────────────────────────────────────────────────
