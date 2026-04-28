package com.centros_sass.admin.adapter.in.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.centros_sass.admin.adapter.in.dto.CreateTenantRequest;
import com.centros_sass.admin.adapter.in.dto.TenantResponse;
import com.centros_sass.admin.adapter.in.dto.UpdateTenantStatusRequest;
import com.centros_sass.admin.adapter.in.mapper.TenantMapper;
import com.centros_sass.admin.application.service.TenantProvisioningService;
import com.centros_sass.admin.application.service.TenantQueryService;
import com.centros_sass.admin.domain.model.TenantStatus;
import com.centros_sass.app.generic.ApiDataResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * TenantController — Endpoints para gestión de tenants en el panel admin.
 *
 * ARQUITECTURA HEXAGONAL:
 * El Controller es el puerto de entrada (adapter in).
 * NO accede al Repository directamente — usa servicios de aplicación.
 *
 * POST → TenantProvisioningService (crear tenant + schema)
 * GET/PATCH/DELETE → TenantQueryService (lectura y modificación)
 *
 * REST API conforme a las convenciones:
 *   - Base path: /api/v1/admin/tenants
 *   - Plural: "tenants"
 *   - Métodos HTTP estándar
 *
 * SEGURIDAD:
 *   - Todas las rutas requieren rol ADMIN (configurado en SecurityConfig)
 */
@RestController
@RequestMapping("/api/v1/admin/tenants")
@RequiredArgsConstructor
public class TenantController {

    private final TenantProvisioningService tenantProvisioningService;
    private final TenantQueryService tenantQueryService;
    private final TenantMapper tenantMapper;

    /**
     * Crea un nuevo tenant.
     *
     * POST /api/v1/admin/tenants
     * Body: CreateTenantRequest(name, slug, adminEmail, planSlug, directorEmail, ...)
     * Respuesta: 201 Created con TenantResponse
     */
    @PostMapping
    public ResponseEntity<ApiDataResponse<TenantResponse>> create(
            @Valid @RequestBody CreateTenantRequest request) {

        var tenant = tenantProvisioningService.provisionTenant(
                request.name(),
                request.slug(),
                request.adminEmail(),
                request.planSlug(),
                request.directorEmail(),
                request.directorPassword(),
                request.directorFirstName(),
                request.directorFirstSurname(),
                request.directorDni(),
                request.directorPhone());

        var response = tenantMapper.toResponse(tenant);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiDataResponse<>("Tenant creado correctamente", response, 201));
    }

    /**
     * Lista todos los tenants paginados.
     *
     * GET /api/v1/admin/tenants
     * Parámetros opcionales:
     *   - ?status=ACTIVE|SUSPENDED|CANCELLED
     *   - ?page=0, ?size=20
     */
    @GetMapping
    public ResponseEntity<ApiDataResponse<Page<TenantResponse>>> findAll(
            @RequestParam(required = false) TenantStatus status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {

        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("createdAt").descending());

        Page<TenantResponse> response;
        if (status != null) {
            response = tenantQueryService.findByStatus(status, pageRequest);
        } else {
            response = tenantQueryService.findAll(pageRequest);
        }

        return ResponseEntity.ok(new ApiDataResponse<>("Lista de tenants", response, 200));
    }

    /**
     * Obtiene un tenant por ID.
     *
     * GET /api/v1/admin/tenants/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiDataResponse<TenantResponse>> findById(@PathVariable Long id) {
        var tenant = tenantQueryService.findById(id);
        var response = tenantMapper.toResponse(tenant);

        return ResponseEntity.ok(new ApiDataResponse<>("Tenant encontrado", response, 200));
    }

    /**
     * Cambia el status de un tenant.
     *
     * PATCH /api/v1/admin/tenants/{id}/status
     * Body: {"status": "SUSPENDED"}
     */
    @PatchMapping("/{id}/status")
    public ResponseEntity<ApiDataResponse<TenantResponse>> updateStatus(
            @PathVariable Long id,
            @Valid @RequestBody UpdateTenantStatusRequest request) {

        var tenant = tenantQueryService.updateStatus(id, request.status());
        var response = tenantMapper.toResponse(tenant);

        return ResponseEntity.ok(new ApiDataResponse<>(
                "Status actualizado a " + request.status(), response, 200));
    }

    /**
     * Elimina un tenant (soft delete).
     *
     * DELETE /api/v1/admin/tenants/{id}
     * Cambia status a CANCELLED.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiDataResponse<Void>> delete(@PathVariable Long id) {
        tenantQueryService.cancelTenant(id);

        return ResponseEntity.ok(new ApiDataResponse<>(
                "Tenant cancelado (soft delete)", null, 200));
    }

}