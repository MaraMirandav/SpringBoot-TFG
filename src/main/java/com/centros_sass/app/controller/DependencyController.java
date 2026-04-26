package com.centros_sass.app.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.centros_sass.app.dto.people.DependencyRequestDTO;
import com.centros_sass.app.dto.people.DependencyResponseDTO;
import com.centros_sass.app.dto.people.DependencyUpdateDTO;
import com.centros_sass.app.exception.ResourceNotFoundException;
import com.centros_sass.app.generic.ApiDataResponse;
import com.centros_sass.app.service.DependencyService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/dependencies")
@RequiredArgsConstructor
public class DependencyController {

    private final DependencyService dependencyService;

    @GetMapping
    public ResponseEntity<ApiDataResponse<List<DependencyResponseDTO>>> findAll(
            @PageableDefault(size = 20) Pageable pageable) {
        Page<DependencyResponseDTO> page = dependencyService.findAll(pageable);
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Niveles de dependencia encontrados",
                page.getContent(),
                HttpStatus.OK.value(),
                (int) page.getTotalElements()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiDataResponse<DependencyResponseDTO>> findById(@PathVariable Integer id) {
        DependencyResponseDTO dependency = dependencyService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Dependency", "id", id));
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Nivel de dependencia encontrado",
                dependency,
                HttpStatus.OK.value()));
    }

    @PostMapping
    public ResponseEntity<ApiDataResponse<DependencyResponseDTO>> create(
            @Valid @RequestBody DependencyRequestDTO dto) {
        DependencyResponseDTO created = dependencyService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiDataResponse<>(
                        "Nivel de dependencia creado exitosamente",
                        created,
                        HttpStatus.CREATED.value()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiDataResponse<DependencyResponseDTO>> update(
            @PathVariable Integer id,
            @Valid @RequestBody DependencyUpdateDTO dto) {
        DependencyResponseDTO updated = dependencyService.update(id, dto)
                .orElseThrow(() -> new ResourceNotFoundException("Dependency", "id", id));
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Nivel de dependencia actualizado",
                updated,
                HttpStatus.OK.value()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiDataResponse<Void>> delete(@PathVariable Integer id) {
        dependencyService.delete(id);
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Nivel de dependencia eliminado",
                HttpStatus.OK.value()));
    }

}
