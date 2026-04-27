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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

import com.centros_sass.app.dto.catalogs.treatments.AllergyRequestDTO;
import com.centros_sass.app.dto.catalogs.treatments.AllergyResponseDTO;
import com.centros_sass.app.dto.catalogs.treatments.AllergyUpdateDTO;
import com.centros_sass.app.exception.ResourceNotFoundException;
import com.centros_sass.app.generic.ApiDataResponse;
import com.centros_sass.app.service.AllergyService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/allergies")
@RequiredArgsConstructor
public class AllergyController {

    private final AllergyService allergyService;

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiDataResponse<List<AllergyResponseDTO>>> findAll(
            @PageableDefault(size = 20) Pageable pageable) {
        Page<AllergyResponseDTO> page = allergyService.findAll(pageable);
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Alergias encontradas",
                page.getContent(),
                HttpStatus.OK.value(),
                (int) page.getTotalElements()));
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiDataResponse<AllergyResponseDTO>> findById(@PathVariable Integer id) {
        AllergyResponseDTO dto = allergyService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Allergy", "id", id));
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Alergia encontrada",
                dto,
                HttpStatus.OK.value()));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'DIRECTOR', 'COORDINADOR')")
    public ResponseEntity<ApiDataResponse<AllergyResponseDTO>> create(
            @Valid @RequestBody AllergyRequestDTO dto) {
        AllergyResponseDTO created = allergyService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiDataResponse<>(
                        "Alergia creada exitosamente",
                        created,
                        HttpStatus.CREATED.value()));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'DIRECTOR', 'COORDINADOR')")
    public ResponseEntity<ApiDataResponse<AllergyResponseDTO>> update(
            @PathVariable Integer id,
            @Valid @RequestBody AllergyUpdateDTO dto) {
        AllergyResponseDTO updated = allergyService.update(id, dto)
                .orElseThrow(() -> new ResourceNotFoundException("Allergy", "id", id));
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Alergia actualizada",
                updated,
                HttpStatus.OK.value()));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'DIRECTOR')")
    public ResponseEntity<ApiDataResponse<Void>> delete(@PathVariable Integer id) {
        allergyService.delete(id);
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Alergia eliminada",
                HttpStatus.OK.value()));
    }

}

