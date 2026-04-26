/*
package com.centros_sass.app.controller.catalogs.treatments;

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

import com.centros_sass.app.dto.catalogs.treatments.StorageConditionRequestDTO;
import com.centros_sass.app.dto.catalogs.treatments.StorageConditionResponseDTO;
import com.centros_sass.app.dto.catalogs.treatments.StorageConditionUpdateDTO;
import com.centros_sass.app.exception.ResourceNotFoundException;
import com.centros_sass.app.generic.ApiDataResponse;
import com.centros_sass.app.service.StorageConditionService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/storage-conditions")
@RequiredArgsConstructor
public class StorageConditionController {

    private final StorageConditionService storageConditionService;

    @GetMapping
    public ResponseEntity<ApiDataResponse<List<StorageConditionResponseDTO>>> findAll(
            @PageableDefault(size = 20) Pageable pageable) {
        Page<StorageConditionResponseDTO> page = storageConditionService.findAll(pageable);
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Condiciones de almacenamiento encontradas",
                page.getContent(),
                HttpStatus.OK.value(),
                (int) page.getTotalElements()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiDataResponse<StorageConditionResponseDTO>> findById(@PathVariable Integer id) {
        StorageConditionResponseDTO dto = storageConditionService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("StorageCondition", "id", id));
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Condición de almacenamiento encontrada",
                dto,
                HttpStatus.OK.value()));
    }

    @PostMapping
    public ResponseEntity<ApiDataResponse<StorageConditionResponseDTO>> create(
            @Valid @RequestBody StorageConditionRequestDTO dto) {
        StorageConditionResponseDTO created = storageConditionService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiDataResponse<>(
                        "Condición de almacenamiento creada exitosamente",
                        created,
                        HttpStatus.CREATED.value()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiDataResponse<StorageConditionResponseDTO>> update(
            @PathVariable Integer id,
            @Valid @RequestBody StorageConditionUpdateDTO dto) {
        StorageConditionResponseDTO updated = storageConditionService.update(id, dto)
                .orElseThrow(() -> new ResourceNotFoundException("StorageCondition", "id", id));
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Condición de almacenamiento actualizada",
                updated,
                HttpStatus.OK.value()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiDataResponse<Void>> delete(@PathVariable Integer id) {
        storageConditionService.delete(id);
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Condición de almacenamiento eliminada",
                HttpStatus.OK.value()));
    }

}

*/
