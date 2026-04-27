package com.centros_sass.admin.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.centros_sass.admin.domain.model.TenantEntity;
import com.centros_sass.admin.domain.model.TenantStatus;

import java.util.List;
import java.util.Optional;

/**
 * TenantRepository — acceso a la tabla tenants en el schema public.
 *
 * Output port en arquitectura hexagonal: la interfaz vive en el dominio (admin.domain),
 * la implementación la genera Spring Data en tiempo de ejecución.
 * Los use cases dependen de esta interfaz, no de JPA directamente.
 */
@Repository
public interface TenantRepository extends JpaRepository<TenantEntity, Long> {

    /**
     * Busca un tenant por slug — operación más frecuente del sistema.
     * Se llama al resolver el tenant_id del JWT a la entidad completa.
     *
     * Optional<T> en vez de T para forzar al caller a manejar "tenant no existe"
     * con orElseThrow() en lugar de NullPointerException en producción.
     *
     * @param slug identificador URL-safe (ej: "acme", "globex")
     * @return el tenant si existe, Optional.empty() si no
     */
    Optional<TenantEntity> findBySlug(String slug);

    /**
     * Lista tenants por estado — útil en el panel admin para filtrar activos,
     * suspendidos o cancelados.
     *
     * @param status ACTIVE, SUSPENDED o CANCELLED
     * @return lista de tenants con ese estado
     */
    List<TenantEntity> findByStatus(TenantStatus status);

    /**
     * Verifica si un slug ya está en uso antes de crear un nuevo tenant.
     * Más eficiente que findBySlug().isPresent(): genera SELECT EXISTS en vez de SELECT *
     * porque no necesita cargar toda la entidad de la BD.
     *
     * @param slug el slug a verificar
     * @return true si ya existe un tenant con ese slug
     */
    boolean existsBySlug(String slug);

    /**
     * Lista todos los tenants paginados.
     *
     * Útil para el panel admin donde hay muchos tenants.
     * Spring genera la query con LIMIT y OFFSET automáticamente.
     *
     * @param pageablePage, size, sort parámetros de paginación
     * @return página de tenants
     */
    Page<TenantEntity> findAll(Pageable pageable);

    /**
     * Lista tenants por estado con paginación.
     *
     * @param status filtrar por estado
     * @param pageable parámetros de paginación
     * @return página de tenants con ese estado
     */
    Page<TenantEntity> findByStatus(TenantStatus status, Pageable pageable);
}

// ─── ¿QUÉ APRENDER DE ESTA CLASE? ────────────────────────────────────────────
// 1. Optional<TenantEntity> findBySlug: fuerza al caller a manejar "no encontrado"
//    Si devolviera TenantEntity directamente, Spring lanzaría null → NPE en producción
//    entity.orElseThrow(() -> new TenantNotFoundException(slug)) es el patrón correcto
// 2. existsBySlug: genera SELECT COUNT(*) > 0 — más eficiente que findBySlug().isPresent()
//    porque no carga toda la entidad de la BD, solo verifica existencia
// 3. Output port hexagonal: esta interfaz define QUÉ necesita el dominio del almacenamiento
//    Spring Data JpaRepository es el adaptador que LO IMPLEMENTA automáticamente
// 4. findByStatus: query derivado del nombre — Spring genera WHERE status = ? automáticamente
// ─────────────────────────────────────────────────────────────────────────────
