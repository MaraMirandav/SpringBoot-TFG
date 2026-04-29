package com.centros_sass.core.tenant;

/**
 * TenantContext — almacena el ID del tenant activo para el hilo de ejecución actual.
 *
 * En una app multitenant, muchos usuarios hacen requests al mismo tiempo.
 * Cada request corre en su propio "hilo" (thread) dentro del servidor.
 * ThreadLocal nos permite guardar un valor POR HILO, sin que se mezcle con otros hilos.
 *
 * Ejemplo: el usuario de "acme" y el usuario de "globex" hacen requests simultáneos.
 * Sin ThreadLocal: ambos compartirían la misma variable → uno vería datos del otro (fuga de datos).
 * Con ThreadLocal: cada hilo tiene su propia copia → total aislamiento.
 *
 * ¿Por qué no es un Spring Bean?
 * Spring gestiona beans en el ApplicationContext (singleton por defecto).
 * Un singleton compartido por todos los hilos es exactamente lo que queremos EVITAR aquí.
 * Por eso es una clase de utilidad estática — se usa como TenantContext.get(), no inyectada.
 *
 * Patrón: Utility Class — constructor privado para que nadie pueda hacer "new TenantContext()".
 */
public final class TenantContext {

    // ThreadLocal garantiza que cada hilo tenga su PROPIA copia de este valor.
    // Sin esto, todos los requests compartirían el mismo String → los tenants se mezclarían.
    // String es el tipo porque el tenant_id en el JWT es un valor de texto (ej: "acme", "globex").
    private static final ThreadLocal<String> CURRENT = new ThreadLocal<>();

    // Constructor privado — impide instanciar esta clase con "new TenantContext()".
    // Solo tiene métodos estáticos, no necesita ser instanciada.
    private TenantContext() {}

    /**
     * Establece el tenant activo para el hilo actual.
     * Llamado por TenantFilter al inicio de cada request, después de parsear el JWT.
     *
     * @param tenantId el identificador único del tenant (ej: "acme", "globex")
     *                 Este valor viene del claim "tenant_id" del JWT del usuario autenticado.
     */
    public static void set(String tenantId) {
        CURRENT.set(tenantId);
    }

    /**
     * Obtiene el tenant activo del hilo actual.
     * Llamado por Hibernate (TenantIdentifierResolver) para saber qué schema usar.
     *
     * @return el tenant_id activo, o null si no se estableció (endpoints sin autenticación)
     */
    public static String get() {
        return CURRENT.get();
    }

    /**
     * Limpia el tenant del hilo actual al finalizar el request.
     *
     * IMPORTANTE: siempre debe llamarse en el bloque finally del filtro.
     * Si no se limpia, el hilo regresa al pool con el tenant_id del request anterior.
     * El siguiente request que reutilice ese hilo heredaría el tenant equivocado — fuga de datos.
     *
     * ¿Por qué .remove() y no .set(null)?
     * .set(null) deja el ThreadLocal activo con valor null → posible memory leak si el hilo
     * vive mucho tiempo. .remove() lo elimina completamente del hilo.
     */
    public static void clear() {
        CURRENT.remove(); // eliminar completamente — no solo poner null
    }
}

// ─── ¿QUÉ APRENDER DE ESTA CLASE? ────────────────────────────
// 1. ThreadLocal: cada hilo tiene su propia copia — fundamental para concurrencia
// 2. Patrón Utility Class: clase final + constructor privado = no se instancia
// 3. Static vs Bean: TenantContext NO es @Component porque un singleton compartido
//    rompería el aislamiento entre tenants en requests concurrentes
// 4. .remove() > .set(null): eliminar del hilo vs. dejar con valor null (memory leak)
// 5. El ciclo de vida: set() en TenantFilter.doFilterInternal() → get() en Hibernate
//    → clear() en el finally del filtro
// ──────────────────────────────────────────────────────────────
