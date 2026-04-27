package com.centros_sass.core.tenant;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.centros_sass.core.exception.TenantResolutionException;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * TenantFilter — filtro HTTP que establece el tenant activo al inicio de cada request.
 *
 * ¿Qué hace este filtro?
 * 1. Intercepta cada request HTTP antes de que llegue al Controller
 * 2. Usa JwtTenantResolver para extraer el tenant_id del JWT ya validado
 * 3. Lo guarda en TenantContext (ThreadLocal) para que Hibernate lo use
 * 4. Al terminar el request, limpia TenantContext (SIEMPRE, en el finally)
 *
 * ¿Por qué OncePerRequestFilter?
 * Spring puede aplicar filtros múltiples veces en ciertos flujos internos (ej: forwards).
 * OncePerRequestFilter garantiza que doFilterInternal() se ejecute UNA SOLA VEZ por request HTTP,
 * evitando duplicar la lógica de extracción del tenant.
 *
 * ¿Por qué @Order(2)?
 * Los filtros se ejecutan en orden numérico. El JwtAuthFilter (@Order(1)) debe ejecutarse primero
 * para validar el JWT y poblar el SecurityContext. Solo entonces este filtro (@Order(2))
 * puede leer el JWT del SecurityContext via JwtTenantResolver.
 *
 * ¿Por qué shouldNotFilter()?
 * Rutas como /auth/login aún no tienen JWT (el usuario todavía no se ha autenticado).
 * Sin shouldNotFilter(), el filtro intentaría extraer tenant_id → NullPointerException o excepción.
 */
@Component
@RequiredArgsConstructor
@Order(2) // ejecutar DESPUÉS del JwtAuthFilter que valida el token (@Order(1))
public class TenantFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(TenantFilter.class);

    // Inyección por constructor (via @RequiredArgsConstructor de Lombok)
    // JwtTenantResolver es el componente que sabe cómo leer el tenant_id del JWT
    private final JwtTenantResolver tenantResolver;

    /**
     * Lógica principal del filtro — se ejecuta una vez por request HTTP.
     *
     * @param request  el request HTTP entrante
     * @param response el response HTTP saliente
     * @param chain    la cadena de filtros — llamar chain.doFilter() para continuar al siguiente filtro o Controller
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {
        try {
            // Extraer tenant_id del JWT en el SecurityContext y guardarlo en el hilo actual
            String tenantId = tenantResolver.resolveTenantId();
            TenantContext.set(tenantId);

            // Continuar con el siguiente filtro o con el Controller
            chain.doFilter(request, response);

        } catch (TenantResolutionException e) {
            log.warn("No se pudo resolver tenant para request {} {}: {}",
                request.getMethod(),
                request.getRequestURI(),
                e.getMessage());
            chain.doFilter(request, response);
            

        } finally {
            // CRÍTICO: limpiar siempre el tenant del hilo, sin importar si hubo error o no.
            // El servidor reutiliza hilos entre requests. Sin esto, el próximo request
            // que use este hilo heredaría el tenant_id del request anterior → fuga de datos.
            TenantContext.clear();
        }
    }

    /**
     * Define qué rutas NO deben pasar por este filtro.
     *
     * Rutas excluidas:
     * - /api/v1/auth/**  → login y refresh token (aún no hay JWT, el usuario se está autenticando)
     * - /api/v1/admin/** → panel admin global (no opera en contexto de tenant específico)
     * - /actuator/**     → health checks de Spring Boot (no requieren tenant)
     *
     * @param request el request HTTP a evaluar
     * @return true si el filtro NO debe aplicarse a esta ruta
     */
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        return path.startsWith("/api/v1/auth")   // autenticación — sin JWT todavía
            || path.startsWith("/api/v1/admin")  // admin global — sin tenant context
            || path.startsWith("/actuator");      // health checks
    }
}

// ─── ¿QUÉ APRENDER DE ESTA CLASE? ────────────────────────────
// 1. OncePerRequestFilter: garantiza ejecución única por request — base de todos los filtros de seguridad
// 2. @Order: los filtros forman una cadena ordenada; el orden importa (JWT primero, luego Tenant)
// 3. try/finally en filtros: el finally es OBLIGATORIO para limpiar estado del hilo
// 4. shouldNotFilter(): patrón para excluir rutas públicas — evita NPE en endpoints sin JWT
// 5. chain.doFilter(): pasar el control al siguiente eslabón de la cadena — sin esto el request muere aquí
// 6. log.warn en catch: los intentos con tokens sin tenant_id quedan registrados
//    — en producción esto alimenta sistemas de detección de intrusiones (SIEM)
//    — sin este log, los ataques son invisibles en los logs del servidor
// ──────────────────────────────────────────────────────────────
