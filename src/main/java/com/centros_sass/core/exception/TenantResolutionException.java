package com.centros_sass.core.exception;

/**
 * TenantResolutionException — excepción lanzada cuando no se puede determinar
 * el tenant del request HTTP actual.
 *
 * ¿Cuándo ocurre esto?
 * - El JWT no contiene el claim "tenant_id"
 * - El JWT está ausente en un endpoint que requiere tenant
 * - El claim "tenant_id" está vacío o en blanco
 *
 * Es una RuntimeException (unchecked) porque representa un estado inválido
 * de seguridad — no queremos que cada método declare "throws TenantResolutionException".
 * El @ControllerAdvice global la capturará y devolverá HTTP 401 o 400.
 *
 * Patrón: dos constructores — uno solo con mensaje, otro con mensaje + causa original.
 * El segundo permite propagar la excepción raíz sin perder el stack trace.
 */
public class TenantResolutionException extends RuntimeException {

    /**
     * Crea la excepción con solo un mensaje descriptivo.
     *
     * @param message descripción del problema (ej: "No tenant_id claim found in JWT")
     */
    public TenantResolutionException(String message) {
        super(message);
    }

    /**
     * Crea la excepción con mensaje Y la causa original.
     * Usar este constructor cuando se captura otra excepción y se re-lanza como
     * TenantResolutionException — así el stack trace completo queda preservado.
     *
     * @param message descripción del problema
     * @param cause   la excepción original que provocó este error
     */
    public TenantResolutionException(String message, Throwable cause) {
        super(message, cause);
    }
}

// ─── ¿QUÉ APRENDER DE ESTA CLASE? ────────────────────────────
// 1. RuntimeException (unchecked): no obliga a declarar "throws" en cada método
// 2. Dos constructores: con mensaje solo vs. mensaje + causa — preservar stack trace
// 3. Excepciones específicas > genéricas: "TenantResolutionException" es más clara que "IllegalStateException"
// 4. Esta excepción será capturada por @ControllerAdvice para devolver HTTP 401/400
// ──────────────────────────────────────────────────────────────
