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

import com.centros_sass.app.dto.worker.WorkerRequestDTO;
import com.centros_sass.app.dto.worker.WorkerResponseDTO;
import com.centros_sass.app.dto.worker.WorkerUpdateDTO;
import com.centros_sass.app.exception.ResourceNotFoundException;
import com.centros_sass.app.generic.ApiDataResponse;
import com.centros_sass.app.service.WorkerService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/workers")
@RequiredArgsConstructor
public class WorkerController {

    private final WorkerService workerService;

    @GetMapping
    public ResponseEntity<ApiDataResponse<List<WorkerResponseDTO>>> findAll(
            @PageableDefault(size = 5) Pageable pageable) {
        Page<WorkerResponseDTO> page = workerService.findAll(pageable);
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Trabajadores activos encontrados",
                page.getContent(),
                HttpStatus.OK.value(),
                (int) page.getTotalElements()));
    }

    @GetMapping("/all")
    public ResponseEntity<ApiDataResponse<List<WorkerResponseDTO>>> findAllIncludingInactive(
            @PageableDefault(size = 20) Pageable pageable) {
        Page<WorkerResponseDTO> page = workerService.findAllIncludingInactive(pageable);
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Todos los trabajadores encontrados",
                page.getContent(),
                HttpStatus.OK.value(),
                (int) page.getTotalElements()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiDataResponse<WorkerResponseDTO>> findById(@PathVariable Integer id) {
        WorkerResponseDTO worker = workerService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Worker", "id", id));
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Trabajador encontrado",
                worker,
                HttpStatus.OK.value()));
    }

    @PostMapping
    public ResponseEntity<ApiDataResponse<WorkerResponseDTO>> create(
            @Valid @RequestBody WorkerRequestDTO dto) {
        WorkerResponseDTO created = workerService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiDataResponse<>(
                        "Trabajador creado exitosamente",
                        created,
                        HttpStatus.CREATED.value()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiDataResponse<WorkerResponseDTO>> update(
            @PathVariable Integer id,
            @Valid @RequestBody WorkerUpdateDTO dto) {
        WorkerResponseDTO updated = workerService.update(id, dto)
                .orElseThrow(() -> new ResourceNotFoundException("Worker", "id", id));
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Trabajador actualizado",
                updated,
                HttpStatus.OK.value()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiDataResponse<WorkerResponseDTO>> delete(@PathVariable Integer id) {
        WorkerResponseDTO deleted = workerService.delete(id)
                .orElseThrow(() -> new ResourceNotFoundException("Worker", "id", id));
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Trabajador eliminado",
                deleted,
                HttpStatus.OK.value()));
    }

}
