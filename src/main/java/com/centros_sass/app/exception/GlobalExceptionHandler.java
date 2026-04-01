package com.centros_sass.app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.centros_sass.app.generic.ApiDataResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

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

    // TODO: Descomentar cuando se active Spring Security en Fase 1B
    // @ExceptionHandler(AccessDeniedException.class)
    // public ResponseEntity<ApiDataResponse<Void>> handleAccessDenied(AccessDeniedException ex) {
    //     return ResponseEntity.status(HttpStatus.FORBIDDEN)
    //             .body(new ApiDataResponse<>("Acceso denegado: no tienes permisos para esta operación", HttpStatus.FORBIDDEN.value()));
    // }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiDataResponse<Void>> handleGeneral(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiDataResponse<>("Error interno del servidor", HttpStatus.INTERNAL_SERVER_ERROR.value()));
    }
}
