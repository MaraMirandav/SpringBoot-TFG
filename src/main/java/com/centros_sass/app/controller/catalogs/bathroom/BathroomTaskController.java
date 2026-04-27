package com.centros_sass.app.controller.catalogs.bathroom;

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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

import com.centros_sass.app.dto.catalogs.bathroom.BathroomTaskRequestDTO;
import com.centros_sass.app.dto.catalogs.bathroom.BathroomTaskResponseDTO;
import com.centros_sass.app.dto.catalogs.bathroom.BathroomTaskUpdateDTO;
import com.centros_sass.app.exception.ResourceNotFoundException;
import com.centros_sass.app.generic.ApiDataResponse;
import com.centros_sass.app.service.BathroomTaskService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/bathroom-tasks")
@RequiredArgsConstructor
public class BathroomTaskController {

    private final BathroomTaskService bathroomTaskService;

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiDataResponse<List<BathroomTaskResponseDTO>>> findAll(
            @PageableDefault(size = 20) Pageable pageable) {
        Page<BathroomTaskResponseDTO> page = bathroomTaskService.findAll(pageable);
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Tareas de higiene encontradas",
                page.getContent(),
                HttpStatus.OK.value(),
                (int) page.getTotalElements()));
    }



    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiDataResponse<BathroomTaskResponseDTO>> findById(@PathVariable Integer id) {
        BathroomTaskResponseDTO task = bathroomTaskService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("BathroomTask", "id", id));
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Tarea de higiene encontrada",
                task,
                HttpStatus.OK.value()));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'DIRECTOR', 'COORDINADOR')")
    public ResponseEntity<ApiDataResponse<BathroomTaskResponseDTO>> create(
            @Valid @RequestBody BathroomTaskRequestDTO dto) {
        BathroomTaskResponseDTO created = bathroomTaskService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiDataResponse<>(
                        "Tarea de higiene creada exitosamente",
                        created,
                        HttpStatus.CREATED.value()));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'DIRECTOR', 'COORDINADOR')")
    public ResponseEntity<ApiDataResponse<BathroomTaskResponseDTO>> update(
            @PathVariable Integer id,
            @Valid @RequestBody BathroomTaskUpdateDTO dto) {
        BathroomTaskResponseDTO updated = bathroomTaskService.update(id, dto)
                .orElseThrow(() -> new ResourceNotFoundException("BathroomTask", "id", id));
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Tarea de higiene actualizada",
                updated,
                HttpStatus.OK.value()));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'DIRECTOR')")
    public ResponseEntity<ApiDataResponse<Void>> delete(@PathVariable Integer id) {
        bathroomTaskService.delete(id);
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Tarea de higiene eliminada",
                HttpStatus.OK.value()));
    }


}
