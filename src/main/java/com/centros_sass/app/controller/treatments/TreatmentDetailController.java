package com.centros_sass.app.controller.treatments;

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

import com.centros_sass.app.dto.treatments.TreatmentDetailRequestDTO;
import com.centros_sass.app.dto.treatments.TreatmentDetailResponseDTO;
import com.centros_sass.app.dto.treatments.TreatmentDetailUpdateDTO;
import com.centros_sass.app.exception.ResourceNotFoundException;
import com.centros_sass.app.generic.ApiDataResponse;
import com.centros_sass.app.service.TreatmentDetailService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/treatment-details")
@RequiredArgsConstructor
public class TreatmentDetailController {

    private final TreatmentDetailService treatmentDetailService;

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiDataResponse<List<TreatmentDetailResponseDTO>>> findAll(
            @PageableDefault(size = 20) Pageable pageable) {
        Page<TreatmentDetailResponseDTO> page = treatmentDetailService.findAll(pageable);
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Tratamientos activos encontrados",
                page.getContent(),
                HttpStatus.OK.value(),
                (int) page.getTotalElements()));
    }

    @GetMapping("/inactive")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiDataResponse<List<TreatmentDetailResponseDTO>>> findAllInactive(
            @PageableDefault(size = 20) Pageable pageable) {
        Page<TreatmentDetailResponseDTO> page = treatmentDetailService.findAllInactive(pageable);
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Tratamientos inactivos encontrados",
                page.getContent(),
                HttpStatus.OK.value(),
                (int) page.getTotalElements()));
    }

    @GetMapping("/all")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiDataResponse<List<TreatmentDetailResponseDTO>>> findAllIncludingInactive(
            @PageableDefault(size = 20) Pageable pageable) {
        Page<TreatmentDetailResponseDTO> page = treatmentDetailService.findAllIncludingInactive(pageable);
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Todos los tratamientos encontrados",
                page.getContent(),
                HttpStatus.OK.value(),
                (int) page.getTotalElements()));
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiDataResponse<TreatmentDetailResponseDTO>> findById(@PathVariable Integer id) {
        TreatmentDetailResponseDTO dto = treatmentDetailService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("TreatmentDetail", "id", id));
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Tratamiento encontrado",
                dto,
                HttpStatus.OK.value()));
    }

    @GetMapping("/active")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiDataResponse<List<TreatmentDetailResponseDTO>>> findActive(
            @PageableDefault(size = 20) Pageable pageable) {
        Page<TreatmentDetailResponseDTO> page = treatmentDetailService.findActive(pageable);
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Tratamientos activos sin fecha de fin",
                page.getContent(),
                HttpStatus.OK.value(),
                (int) page.getTotalElements()));
    }

    @GetMapping("/medication/{medicationId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiDataResponse<List<TreatmentDetailResponseDTO>>> findByMedication(
            @PathVariable Integer medicationId,
            @PageableDefault(size = 20) Pageable pageable) {
        Page<TreatmentDetailResponseDTO> page = treatmentDetailService.findByMedicationId(medicationId, pageable);
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Tratamientos del medicamento encontrados",
                page.getContent(),
                HttpStatus.OK.value(),
                (int) page.getTotalElements()));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'DIRECTOR', 'COORDINADOR', 'ENFERMERO', 'FISIO', 'TO', 'TS', 'PSICO', 'TAS')")
    public ResponseEntity<ApiDataResponse<TreatmentDetailResponseDTO>> create(
            @Valid @RequestBody TreatmentDetailRequestDTO dto) {
        TreatmentDetailResponseDTO created = treatmentDetailService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiDataResponse<>(
                        "Tratamiento creado exitosamente",
                        created,
                        HttpStatus.CREATED.value()));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'DIRECTOR', 'COORDINADOR', 'ENFERMERO', 'FISIO', 'TO', 'TS', 'PSICO', 'TAS')")
    public ResponseEntity<ApiDataResponse<TreatmentDetailResponseDTO>> update(
            @PathVariable Integer id,
            @Valid @RequestBody TreatmentDetailUpdateDTO dto) {
        TreatmentDetailResponseDTO updated = treatmentDetailService.update(id, dto)
                .orElseThrow(() -> new ResourceNotFoundException("TreatmentDetail", "id", id));
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Tratamiento actualizado",
                updated,
                HttpStatus.OK.value()));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'DIRECTOR')")
    public ResponseEntity<ApiDataResponse<TreatmentDetailResponseDTO>> delete(@PathVariable Integer id) {
        TreatmentDetailResponseDTO deleted = treatmentDetailService.delete(id)
                .orElseThrow(() -> new ResourceNotFoundException("TreatmentDetail", "id", id));
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Tratamiento eliminado",
                deleted,
                HttpStatus.OK.value()));
    }

}
