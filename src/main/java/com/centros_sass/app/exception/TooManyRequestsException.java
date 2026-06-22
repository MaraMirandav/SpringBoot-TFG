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
