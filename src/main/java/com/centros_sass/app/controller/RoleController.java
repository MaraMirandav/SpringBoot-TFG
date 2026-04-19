package com.centros_sass.app.controller;

import java.util.List;

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

import com.centros_sass.app.dto.role.RoleRequestDTO;
import com.centros_sass.app.dto.role.RoleResponseDTO;
import com.centros_sass.app.dto.role.RoleUpdateDTO;
import com.centros_sass.app.exception.ResourceNotFoundException;
import com.centros_sass.app.generic.ApiDataResponse;
import com.centros_sass.app.service.RoleService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @GetMapping
    public ResponseEntity<ApiDataResponse<List<RoleResponseDTO>>> findAll() {
        List<RoleResponseDTO> roles = roleService.findAll();
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Roles encontrados",
                roles,
                HttpStatus.OK.value(),
                roles.size()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiDataResponse<RoleResponseDTO>> findById(@PathVariable Integer id) {
        RoleResponseDTO role = roleService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role", "id", id));
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Rol encontrado",
                role,
                HttpStatus.OK.value()));
    }

    @PostMapping
    public ResponseEntity<ApiDataResponse<RoleResponseDTO>> create(
            @Valid @RequestBody RoleRequestDTO dto) {
        RoleResponseDTO created = roleService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiDataResponse<>(
                        "Rol creado exitosamente",
                        created,
                        HttpStatus.CREATED.value()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiDataResponse<RoleResponseDTO>> update(
            @PathVariable Integer id,
            @Valid @RequestBody RoleUpdateDTO dto) {
        RoleResponseDTO updated = roleService.update(id, dto)
                .orElseThrow(() -> new ResourceNotFoundException("Role", "id", id));
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Rol actualizado",
                updated,
                HttpStatus.OK.value()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiDataResponse<RoleResponseDTO>> delete(@PathVariable Integer id) {
        RoleResponseDTO deleted = roleService.delete(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role", "id", id));
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Rol eliminado",
                deleted,
                HttpStatus.OK.value()));
    }

}