package com.centros_sass.app.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/")
public class HomeController {

    @GetMapping("health")
    public ResponseEntity<Map<String, String>> health() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "UP");
        response.put("version", "1.0.0");
        response.put("message", "Mi primera API funcionando!!!!");
        return ResponseEntity.ok(response);
    }

    @GetMapping("error")
    public ResponseEntity<Map<String, String>> error() {
        Map<String, String> body = new HashMap<>();
        body.put("status", "DOWN");
        body.put("version", "1.0.0");
        body.put("message", "Mi primera API sin encontrar nada!!!!");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }

}
