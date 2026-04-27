package com.centros_sass.admin.application.service;

import com.centros_sass.admin.adapter.in.dto.TenantResponse;
import com.centros_sass.admin.adapter.in.mapper.TenantMapper;
import com.centros_sass.admin.domain.model.TenantEntity;
import com.centros_sass.admin.domain.model.TenantStatus;
import com.centros_sass.admin.domain.repository.TenantRepository;
import com.centros_sass.app.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * TenantQueryService — servicio para consultas y actualizaciones de tenants.
 *
 * Puerto de aplicación (use case) en arquitectura hexagonal.
 * Este servicio encapsula toda la lógica de lectura y modificación
 * de tenants que no sea provisioning (crear schema).
 *
 * El Controller NUNCA accede al repository directamente.
 * Se comunica con el mundo exterior através de este servicio.
 */
@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class TenantQueryService {

    private final TenantRepository tenantRepository;
    private final TenantMapper tenantMapper;

    /**
     * Busca un tenant por ID.
     *
     * @param id ID del tenant
     * @return el TenantEntity
     * @throws ResourceNotFoundException si no existe
     */
    public TenantEntity findById(Long id) {
        return tenantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tenant", "id", id));
    }

    /**
     * Lista todos los tenants paginados.
     *
     * @param pageable parámetros de paginación
     * @return página de TenantResponse
     */
    public Page<TenantResponse> findAll(Pageable pageable) {
        return tenantRepository.findAll(pageable)
                .map(tenantMapper::toResponse);
    }

    /**
     * Lista tenants por estado con paginación.
     *
     * @param status filtrar por estado
     * @param pageable parámetros de paginación
     * @return página de TenantResponse
     */
    public Page<TenantResponse> findByStatus(TenantStatus status, Pageable pageable) {
        return tenantRepository.findByStatus(status, pageable)
                .map(tenantMapper::toResponse);
    }

    /**
     * Actualiza el status de un tenant.
     *
     * @param id ID del tenant
     * @param newStatus nuevo status
     * @return el TenantEntity actualizado
     */
    public TenantEntity updateStatus(Long id, TenantStatus newStatus) {
        var tenant = findById(id);
        tenant.setStatus(newStatus);
        log.info("Tenant {} status actualizado a {}", id, newStatus);
        return tenantRepository.save(tenant);
    }

    /**
     * Cancela un tenant (soft delete).
     *
     * Cambia el status a CANCELLED.
     * NO borra el schema ni los datos.
     *
     * @param id ID del tenant
     */
    public void cancelTenant(Long id) {
        var tenant = findById(id);
        tenant.setStatus(TenantStatus.CANCELLED);
        tenantRepository.save(tenant);
        log.info("Tenant {} cancelado (soft delete)", id);
    }
}