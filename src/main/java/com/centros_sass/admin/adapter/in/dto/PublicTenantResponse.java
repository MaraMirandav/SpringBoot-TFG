package com.centros_sass.admin.adapter.in.dto;

/**
 * PublicTenantResponse — DTO público con información mínima de un tenant activo.
 *
 * Se usa en el endpoint GET /api/v1/admin/tenants/public, que es de acceso libre
 * (sin autenticación) y permite al frontend mostrar la lista de centros disponibles
 * en la pantalla de login, para que el usuario seleccione su centro antes de autenticarse.
 *
 * ¿Por qué no reutilizar TenantResponse?
 * TenantResponse incluye adminEmail y createdAt — datos internos del panel admin
 * que no deben exponerse públicamente. Este DTO expone solo lo mínimo necesario:
 * id (referencia), name (texto visible) y slug (identificador para el login).
 */
public record PublicTenantResponse(Long id, String name, String slug) {}
