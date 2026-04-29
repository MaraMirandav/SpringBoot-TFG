package com.centros_sass.app.exception;

/**
 * TooManyRequestsException — excepción de dominio para límite de intentos de login.
 *
 * Se lanza cuando una IP supera el número máximo de intentos fallidos en un período
 * de tiempo determinado (rate limiting). LoginRateLimiter (PASO 2) es quien la lanza.
 *
 * HTTP mapping: GlobalExceptionHandler la captura y devuelve HTTP 429 Too Many Requests.
 *
 * ¿Por qué una excepción propia en lugar de lanzar directamente una ResponseEntity?
 * Separación de responsabilidades: LoginRateLimiter pertenece a la capa de seguridad
 * y NO debe conocer HTTP ni Spring MVC. Solo lanza esta excepción, y GlobalExceptionHandler
 * se encarga de convertirla en la respuesta HTTP correcta.
 * Esto hace LoginRateLimiter más testeable y desacoplado de la capa web.
 */
public class TooManyRequestsException extends RuntimeException {

    /**
     * Crea la excepción con un mensaje descriptivo para el usuario.
     *
     * @param message mensaje que explica el límite y cuándo puede reintentar
     *                (ej: "Demasiados intentos fallidos. Inténtalo en 15 minutos.")
     *                Este mensaje llega al cliente en el body de la respuesta 429.
     */
    public TooManyRequestsException(String message) {
        super(message);
    }
}

// ─── ¿QUÉ APRENDER DE ESTA CLASE? ────────────────────────────
// 1. RuntimeException: no obliga a declarar "throws" en cada método que la use
//    (unchecked exception) — adecuada para errores de negocio en Spring
// 2. Excepción de dominio vs. excepción HTTP: LoginRateLimiter lanza esta excepción,
//    GlobalExceptionHandler la convierte en 429 — separación de responsabilidades
// 3. Patrón exception-per-concept: una clase por tipo de error de negocio,
//    en vez de reutilizar RuntimeException con distintos mensajes
// ──────────────────────────────────────────────────────────────
