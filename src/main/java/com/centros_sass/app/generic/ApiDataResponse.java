package com.centros_sass.app.generic;

public record ApiDataResponse<T>(String message, T data, int status) {

    public ApiDataResponse(T data) {
        this("Operación exitosa", data, 200);
    }

    public ApiDataResponse(String message, int status) {
        this(message, null, status);
    }
}
