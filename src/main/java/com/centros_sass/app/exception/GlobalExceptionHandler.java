package com.centros_sass.app.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.centros_sass.app.generic.ApiDataResponse;

/**
 * GlobalExceptionHandler — captura todas las excepciones de la app y las convierte
 * en respuestas HTTP con el formato estándar ApiDataResponse.
 *
 * ¿Por qué @RestControllerAdvice?
 * Sin esto, Spring devolvería stacktraces en texto plano al cliente — inaceptable en producción.
 * @RestControllerAdvice intercepta cualquier excepción lanzada desde un @Controller o @Service
 * y la convierte en JSON con el código HTTP correcto.
 *
 * Orden de los handlers: Spring aplica el handler MÁS ESPECÍFICO que coincida.
 * Por eso el handler genérico (Exception.class) debe ir al final — es el "catch-all".
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiDataResponse<Void>> handleNotFound(ResourceNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiDataResponse<>(ex.getMessage(), HttpStatus.NOT_FOUND.value()));
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiDataResponse<Void>> handleBadRequest(BadRequestException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ApiDataResponse<>(ex.getMessage(), HttpStatus.BAD_REQUEST.value()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiDataResponse<Void>> handleValidation(MethodArgumentNotValidException ex) {
        StringBuilder message = new StringBuilder("Error de validación: ");
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            message.append(error.getField())
                   .append(" ")
                   .append(error.getDefaultMessage())
                   .append("; ");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ApiDataResponse<>(message.toString(), HttpStatus.BAD_REQUEST.value()));
    }

    @ExceptionHandler(org.springframework.security.access.AccessDeniedException.class)
    public ResponseEntity<ApiDataResponse<Void>> handleAccessDenied(org.springframework.security.access.AccessDeniedException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(new ApiDataResponse<>("Acceso denegado: no tienes permisos para esta operación", HttpStatus.FORBIDDEN.value()));
    }

    /**
     * Captura errores de autenticación — credenciales incorrectas o usuario no encontrado.
     *
     * ¿Por qué el mismo mensaje para ambas excepciones?
     * Anti-enumeration de usuarios: si dijésemos "email no encontrado" vs "password incorrecto",
     * un atacante podría descubrir qué emails existen en el sistema simplemente probando.
     * Con un mensaje genérico idéntico para ambos casos, no se filtra esa información.
     *
     * UsernameNotFoundException: el email no existe en el schema del tenant.
     * BadCredentialsException: el password no coincide con el hash en BD.
     */
    @ExceptionHandler({UsernameNotFoundException.class, BadCredentialsException.class})
    public ResponseEntity<ApiDataResponse<Void>> handleAuthException(Exception ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new ApiDataResponse<>("Credenciales inválidas", HttpStatus.UNAUTHORIZED.value()));
    }

    /**
     * Captura el exceso de intentos de login desde la misma IP.
     *
     * ¿Por qué propagar ex.getMessage() aquí y no en handleAuthException?
     * En el rate limit SÍ queremos informar al usuario cuánto tiempo debe esperar
     * (ej: "Inténtalo en 15 minutos") — no revela información sensible del sistema,
     * solo la política pública de límite de intentos.
     */
    @ExceptionHandler(TooManyRequestsException.class)
    public ResponseEntity<ApiDataResponse<Void>> handleRateLimit(TooManyRequestsException ex) {
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS)
                .body(new ApiDataResponse<>(ex.getMessage(), HttpStatus.TOO_MANY_REQUESTS.value()));
    }

    /**
     * Handler de último recurso — captura cualquier excepción no manejada arriba.
     *
     * ¿Por qué devolver siempre el mismo mensaje genérico?
     * El stacktrace o el mensaje interno puede revelar estructura interna del sistema.
     * En producción, los detalles van al log del servidor, no al cliente.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiDataResponse<Void>> handleGeneral(Exception ex) {
        log.error("UNHANDLED_EXCEPTION: {} - {}", ex.getClass().getSimpleName(), ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiDataResponse<>("Error interno del servidor", HttpStatus.INTERNAL_SERVER_ERROR.value()));
    }
}
