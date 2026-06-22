package com.centros_sass.admin.adapter.in.controller;

import com.centros_sass.admin.adapter.in.dto.PublicPlanResponse;
import com.centros_sass.admin.adapter.in.dto.PublicTenantResponse;
import com.centros_sass.admin.application.service.PlanService;
import com.centros_sass.admin.application.service.TenantQueryService;
import com.centros_sass.app.generic.ApiDataResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * PublicController — endpoints accesibles sin autenticación.
 *
 * ¿Por qué un controller separado en vez de añadir rutas a TenantController?
 * TenantController vive bajo /api/v1/admin/**, que Spring Security protege
 * con hasAnyRole("ADMIN"...) de forma global. Aunque añadamos ese path concreto
 * al bloque permitAll(), Spring evalúa los matchers en orden de registro y
 * el wildcard /api/v1/admin/** se aplica primero → 401.
 *
 * Mover los endpoints públicos a /api/v1/public/** resuelve el problema
 * con una sola regla de seguridad clara y sin ambigüedad:
 *   - /api/v1/public/**  → permitAll()  (este controller)
 *   - /api/v1/admin/**   → hasAnyRole() (TenantController, AdminAuthController, etc.)
 *
 * Responsabilidad: exponer datos de solo lectura que el frontend necesita
 * ANTES de que el usuario esté autenticado (ej: lista de centros en la pantalla de login).
 */
@RestController
@RequestMapping("/api/v1/public")
@RequiredArgsConstructor
public class PublicController {

    private final TenantQueryService tenantQueryService;
    private final PlanService planService;

    /**
     * Lista los tenants activos con datos mínimos — acceso libre, sin JWT.
     *
     * GET /api/v1/public/tenants
     *
     * Uso típico: el frontend muestra un desplegable con los centros disponibles
     * en la pantalla de login. El usuario elige su centro → el slug se envía
     * como campo "tenantId" en el request de login.
     *
     * Devuelve PublicTenantResponse (id, name, slug) y NO TenantResponse,
     * para no exponer adminEmail ni createdAt de forma pública.
     *
     * @return lista de centros activos ordenados por nombre (orden de BD)
     */
    @GetMapping("/tenants")
    public ResponseEntity<ApiDataResponse<List<PublicTenantResponse>>> findAllPublic() {
        return ResponseEntity.ok(
                new ApiDataResponse<>("Centros disponibles",
                        tenantQueryService.findAllActive(), 200));
    }

    @GetMapping("/plans")
    public ResponseEntity<ApiDataResponse<List<PublicPlanResponse>>> findAllPlans() {
        return ResponseEntity.ok(
                new ApiDataResponse<>("Planes disponibles",
                        planService.findAllActive(), 200));
    }
}
