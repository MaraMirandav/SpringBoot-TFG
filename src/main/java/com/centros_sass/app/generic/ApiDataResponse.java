package com.centros_sass.app.generic;

public record ApiDataResponse<T>(String message, T data, Integer status, Integer totalElements) {

    public ApiDataResponse(String message, T data, Integer status) {
        this(message, data, status, null);
    }

    public ApiDataResponse(String message, Integer status) {
        this(message, null, status, null);
    }

}
