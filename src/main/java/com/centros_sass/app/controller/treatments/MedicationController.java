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

import com.centros_sass.app.dto.treatments.MedicationRequestDTO;
import com.centros_sass.app.dto.treatments.MedicationResponseDTO;
import com.centros_sass.app.dto.treatments.MedicationUpdateDTO;
import com.centros_sass.app.exception.ResourceNotFoundException;
import com.centros_sass.app.generic.ApiDataResponse;
import com.centros_sass.app.service.MedicationService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/medications")
@RequiredArgsConstructor
public class MedicationController {

    private final MedicationService medicationService;

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiDataResponse<List<MedicationResponseDTO>>> findAll(
            @PageableDefault(size = 20) Pageable pageable) {
        Page<MedicationResponseDTO> page = medicationService.findAll(pageable);
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Medicamentos activos encontrados",
                page.getContent(),
                HttpStatus.OK.value(),
                (int) page.getTotalElements()));
    }

    @GetMapping("/inactive")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiDataResponse<List<MedicationResponseDTO>>> findAllInactive(
            @PageableDefault(size = 20) Pageable pageable) {
        Page<MedicationResponseDTO> page = medicationService.findAllInactive(pageable);
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Medicamentos inactivos encontrados",
                page.getContent(),
                HttpStatus.OK.value(),
                (int) page.getTotalElements()));
    }

    @GetMapping("/all")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiDataResponse<List<MedicationResponseDTO>>> findAllIncludingInactive(
            @PageableDefault(size = 20) Pageable pageable) {
        Page<MedicationResponseDTO> page = medicationService.findAllIncludingInactive(pageable);
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Todos los medicamentos encontrados",
                page.getContent(),
                HttpStatus.OK.value(),
                (int) page.getTotalElements()));
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiDataResponse<MedicationResponseDTO>> findById(@PathVariable Integer id) {
        MedicationResponseDTO dto = medicationService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Medication", "id", id));
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Medicamento encontrado",
                dto,
                HttpStatus.OK.value()));
    }

    @GetMapping("/user/{userId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiDataResponse<List<MedicationResponseDTO>>> findByUser(
            @PathVariable Integer userId,
            @PageableDefault(size = 20) Pageable pageable) {
        Page<MedicationResponseDTO> page = medicationService.findByUserId(userId, pageable);
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Medicamentos del usuario encontrados",
                page.getContent(),
                HttpStatus.OK.value(),
                (int) page.getTotalElements()));
    }

    @GetMapping("/expiring-soon")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiDataResponse<List<MedicationResponseDTO>>> findExpiringSoon(
            @PageableDefault(size = 20) Pageable pageable) {
        Page<MedicationResponseDTO> page = medicationService.findExpiringSoon(pageable);
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Medicamentos próximos a caducar",
                page.getContent(),
                HttpStatus.OK.value(),
                (int) page.getTotalElements()));
    }

    @GetMapping("/low-stock")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiDataResponse<List<MedicationResponseDTO>>> findLowStock(
            @PageableDefault(size = 20) Pageable pageable) {
        Page<MedicationResponseDTO> page = medicationService.findLowStock(pageable);
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Medicamentos con stock bajo",
                page.getContent(),
                HttpStatus.OK.value(),
                (int) page.getTotalElements()));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'DIRECTOR', 'COORDINADOR', 'ENFERMERO', 'FISIO', 'TO', 'TS', 'PSICO', 'TAS')")
    public ResponseEntity<ApiDataResponse<MedicationResponseDTO>> create(
            @Valid @RequestBody MedicationRequestDTO dto) {
        MedicationResponseDTO created = medicationService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiDataResponse<>(
                        "Medicamento creado exitosamente",
                        created,
                        HttpStatus.CREATED.value()));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'DIRECTOR', 'COORDINADOR', 'ENFERMERO', 'FISIO', 'TO', 'TS', 'PSICO', 'TAS')")
    public ResponseEntity<ApiDataResponse<MedicationResponseDTO>> update(
            @PathVariable Integer id,
            @Valid @RequestBody MedicationUpdateDTO dto) {
        MedicationResponseDTO updated = medicationService.update(id, dto)
                .orElseThrow(() -> new ResourceNotFoundException("Medication", "id", id));
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Medicamento actualizado",
                updated,
                HttpStatus.OK.value()));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'DIRECTOR')")
    public ResponseEntity<ApiDataResponse<MedicationResponseDTO>> delete(@PathVariable Integer id) {
        MedicationResponseDTO deleted = medicationService.delete(id)
                .orElseThrow(() -> new ResourceNotFoundException("Medication", "id", id));
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Medicamento eliminado",
                deleted,
                HttpStatus.OK.value()));
    }

}
