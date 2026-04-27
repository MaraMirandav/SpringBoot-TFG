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

import com.centros_sass.app.dto.catalogs.treatments.MedicationNameRequestDTO;
import com.centros_sass.app.dto.catalogs.treatments.MedicationNameResponseDTO;
import com.centros_sass.app.dto.catalogs.treatments.MedicationNameUpdateDTO;
import com.centros_sass.app.exception.ResourceNotFoundException;
import com.centros_sass.app.generic.ApiDataResponse;
import com.centros_sass.app.service.MedicationNameService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/medication-names")
@RequiredArgsConstructor
public class MedicationNameController {

    private final MedicationNameService medicationNameService;

    @GetMapping
    public ResponseEntity<ApiDataResponse<List<MedicationNameResponseDTO>>> findAll(
            @PageableDefault(size = 20) Pageable pageable) {
        Page<MedicationNameResponseDTO> page = medicationNameService.findAll(pageable);
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Medicamentos encontradas",
                page.getContent(),
                HttpStatus.OK.value(),
                (int) page.getTotalElements()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiDataResponse<MedicationNameResponseDTO>> findById(@PathVariable Integer id) {
        MedicationNameResponseDTO dto = medicationNameService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("MedicationName", "id", id));
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Medicamento encontrada",
                dto,
                HttpStatus.OK.value()));
    }

    @PostMapping
    public ResponseEntity<ApiDataResponse<MedicationNameResponseDTO>> create(
            @Valid @RequestBody MedicationNameRequestDTO dto) {
        MedicationNameResponseDTO created = medicationNameService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiDataResponse<>(
                        "Medicamento creada exitosamente",
                        created,
                        HttpStatus.CREATED.value()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiDataResponse<MedicationNameResponseDTO>> update(
            @PathVariable Integer id,
            @Valid @RequestBody MedicationNameUpdateDTO dto) {
        MedicationNameResponseDTO updated = medicationNameService.update(id, dto)
                .orElseThrow(() -> new ResourceNotFoundException("MedicationName", "id", id));
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Medicamento actualizada",
                updated,
                HttpStatus.OK.value()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiDataResponse<Void>> delete(@PathVariable Integer id) {
        medicationNameService.delete(id);
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Medicamento eliminada",
                HttpStatus.OK.value()));
    }

}

