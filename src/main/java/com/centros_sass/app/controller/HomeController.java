package com.centros_sass.app.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.centros_sass.app.generic.ApiDataResponse;

@RestController
@RequestMapping("/")
public class HomeController {

    @GetMapping("health")
    public ResponseEntity<ApiDataResponse<Map<String, String>>> health() {
        Map<String, String> data = new HashMap<>();
        data.put("status", "UP");
        data.put("version", "1.0.0");
        return ResponseEntity.ok(new ApiDataResponse<>("API funcionando correctamente", data, 200));
    }

    @GetMapping("error")
    public ResponseEntity<ApiDataResponse<Void>> error() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiDataResponse<>("Recurso no encontrado", HttpStatus.NOT_FOUND.value()));
    }

    @GetMapping("/home")
    public ResponseEntity<ApiDataResponse<String>> home() {
        return ResponseEntity.ok(new ApiDataResponse<>("Bienvenido a la API, si ves esto, estas logeado!", 200));
    }

}
