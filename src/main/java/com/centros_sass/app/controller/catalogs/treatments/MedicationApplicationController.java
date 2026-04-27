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

import com.centros_sass.app.dto.catalogs.treatments.MedicationApplicationRequestDTO;
import com.centros_sass.app.dto.catalogs.treatments.MedicationApplicationResponseDTO;
import com.centros_sass.app.dto.catalogs.treatments.MedicationApplicationUpdateDTO;
import com.centros_sass.app.exception.ResourceNotFoundException;
import com.centros_sass.app.generic.ApiDataResponse;
import com.centros_sass.app.service.MedicationApplicationService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/medication-applications")
@RequiredArgsConstructor
public class MedicationApplicationController {

    private final MedicationApplicationService medicationApplicationService;

    @GetMapping
    public ResponseEntity<ApiDataResponse<List<MedicationApplicationResponseDTO>>> findAll(
            @PageableDefault(size = 20) Pageable pageable) {
        Page<MedicationApplicationResponseDTO> page = medicationApplicationService.findAll(pageable);
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Aplicaciones de medicamento encontradas",
                page.getContent(),
                HttpStatus.OK.value(),
                (int) page.getTotalElements()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiDataResponse<MedicationApplicationResponseDTO>> findById(@PathVariable Integer id) {
        MedicationApplicationResponseDTO dto = medicationApplicationService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("MedicationApplication", "id", id));
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Aplicación de medicamento encontrada",
                dto,
                HttpStatus.OK.value()));
    }

    @PostMapping
    public ResponseEntity<ApiDataResponse<MedicationApplicationResponseDTO>> create(
            @Valid @RequestBody MedicationApplicationRequestDTO dto) {
        MedicationApplicationResponseDTO created = medicationApplicationService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiDataResponse<>(
                        "Aplicación de medicamento creada exitosamente",
                        created,
                        HttpStatus.CREATED.value()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiDataResponse<MedicationApplicationResponseDTO>> update(
            @PathVariable Integer id,
            @Valid @RequestBody MedicationApplicationUpdateDTO dto) {
        MedicationApplicationResponseDTO updated = medicationApplicationService.update(id, dto)
                .orElseThrow(() -> new ResourceNotFoundException("MedicationApplication", "id", id));
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Aplicación de medicamento actualizada",
                updated,
                HttpStatus.OK.value()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiDataResponse<Void>> delete(@PathVariable Integer id) {
        medicationApplicationService.delete(id);
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Aplicación de medicamento eliminada",
                HttpStatus.OK.value()));
    }

}

