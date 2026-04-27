package com.centros_sass.core.tenant;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.stereotype.Component;

/**
 * TenantIdentifierResolver — le dice a Hibernate qué tenant (schema) usar para la sesión actual.
 *
 * ¿Por qué existe esta clase?
 * Hibernate necesita saber en qué schema de PostgreSQL debe ejecutar las queries.
 * Esta clase implementa la interfaz CurrentTenantIdentifierResolver de Hibernate,
 * que es el punto de extensión oficial para multitenancy.
 *
 * ¿Cómo funciona la integración?
 * 1. TenantFilter llama TenantContext.set("acme") al inicio del request
 * 2. Hibernate llama resolveCurrentTenantIdentifier() antes de abrir una sesión
 * 3. Esta clase lee TenantContext.get() → devuelve "acme"
 * 4. Hibernate pasa "acme" a MultiTenantConnectionProviderImpl.getConnection("acme")
 * 5. La conexión ejecuta SET search_path TO tenant_acme, public
 *
 * ¿Por qué CurrentTenantIdentifierResolver<String> con genérico?
 * Hibernate 6 (incluido en Spring Boot 3.x) usa generics para el tipo del identificador.
 * El identificador de tenant es un String (ej: "acme", "globex").
 * Sin el <String>, hay warnings de tipo raw en Hibernate 6.
 *
 * Fallback a "public":
 * Si TenantContext.get() devuelve null (endpoint sin tenant, ej: /actuator),
 * se retorna "public" para que Hibernate use el schema global — evita NullPointerException.
 */
@Component
public class TenantIdentifierResolver implements CurrentTenantIdentifierResolver<String> {

    /**
     * Retorna el identificador del tenant activo para la sesión Hibernate actual.
     * Hibernate llama este método automáticamente antes de abrir cada sesión de base de datos.
     *
     * @return el tenant_id del hilo actual (ej: "acme"), o "public" si no hay tenant activo
     */
    @Override
    public String resolveCurrentTenantIdentifier() {
        // Leer el tenant_id que TenantFilter guardó en este hilo
        // TenantContext.get() es una llamada estática — no inyectada como bean
        String tenant = TenantContext.get();

        System.out.println(">>> RESOLVER: tenant=" + tenant +
                           " thread=" + Thread.currentThread().getName());

        // Si hay tenant activo, usarlo; si no (null), usar el schema global "public"
        // El schema "public" contiene las tablas globales: tenants, plans, audit_log
        return (tenant != null) ? tenant : "public";
    }

    /**
     * Indica a Hibernate si debe validar que las sesiones existentes
     * pertenecen al tenant activo antes de reutilizarlas.
     *
     * Retornar true es la opción segura: Hibernate verificará que no se mezclen
     * sesiones de diferentes tenants al reutilizar conexiones del pool.
     *
     * @return true — siempre validar el tenant de las sesiones existentes
     */
    @Override
    public boolean validateExistingCurrentSessions() {
        return true; // seguridad: validar siempre que la sesión pertenece al tenant correcto
    }
}

// ─── ¿QUÉ APRENDER DE ESTA CLASE? ────────────────────────────
// 1. CurrentTenantIdentifierResolver<String>: punto de extensión de Hibernate para multitenancy
//    — implementar esta interfaz es lo que "conecta" TenantContext con Hibernate
// 2. Genéricos en Hibernate 6: siempre usar <String> (o el tipo de tu identificador)
//    para evitar raw type warnings — diferencia importante con Hibernate 5
// 3. Fallback a "public": nunca retornar null desde resolveCurrentTenantIdentifier()
//    — null causaría NullPointerException dentro de Hibernate
// 4. TenantContext.get() como llamada estática: esta clase usa TenantContext correctamente,
//    sin inyectarlo como bean (lo que fallaría con NoSuchBeanDefinitionException)
// 5. validateExistingCurrentSessions() = true: opción más segura en producción
// ──────────────────────────────────────────────────────────────
