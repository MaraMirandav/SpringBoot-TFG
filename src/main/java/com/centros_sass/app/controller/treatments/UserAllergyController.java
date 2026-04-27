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

import com.centros_sass.app.dto.treatments.UserAllergyRequestDTO;
import com.centros_sass.app.dto.treatments.UserAllergyResponseDTO;
import com.centros_sass.app.dto.treatments.UserAllergyUpdateDTO;
import com.centros_sass.app.exception.ResourceNotFoundException;
import com.centros_sass.app.generic.ApiDataResponse;
import com.centros_sass.app.service.UserAllergyService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/user-allergies")
@RequiredArgsConstructor
public class UserAllergyController {

    private final UserAllergyService userAllergyService;

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiDataResponse<List<UserAllergyResponseDTO>>> findAll(
            @PageableDefault(size = 20) Pageable pageable) {
        Page<UserAllergyResponseDTO> page = userAllergyService.findAll(pageable);
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Alergias activas encontradas",
                page.getContent(),
                HttpStatus.OK.value(),
                (int) page.getTotalElements()));
    }

    @GetMapping("/inactive")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiDataResponse<List<UserAllergyResponseDTO>>> findAllInactive(
            @PageableDefault(size = 20) Pageable pageable) {
        Page<UserAllergyResponseDTO> page = userAllergyService.findAllInactive(pageable);
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Alergias inactivas encontradas",
                page.getContent(),
                HttpStatus.OK.value(),
                (int) page.getTotalElements()));
    }

    @GetMapping("/all")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiDataResponse<List<UserAllergyResponseDTO>>> findAllIncludingInactive(
            @PageableDefault(size = 20) Pageable pageable) {
        Page<UserAllergyResponseDTO> page = userAllergyService.findAllIncludingInactive(pageable);
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Todas las alergias encontradas",
                page.getContent(),
                HttpStatus.OK.value(),
                (int) page.getTotalElements()));
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiDataResponse<UserAllergyResponseDTO>> findById(@PathVariable Integer id) {
        UserAllergyResponseDTO dto = userAllergyService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("UserAllergy", "id", id));
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Alergia encontrada",
                dto,
                HttpStatus.OK.value()));
    }

    @GetMapping("/user-medical-info/{infoId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiDataResponse<List<UserAllergyResponseDTO>>> findByUserMedicalInfo(
            @PathVariable Integer infoId,
            @PageableDefault(size = 20) Pageable pageable) {
        Page<UserAllergyResponseDTO> page = userAllergyService.findByUserMedicalInfoId(infoId, pageable);
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Alergias de la ficha médica encontradas",
                page.getContent(),
                HttpStatus.OK.value(),
                (int) page.getTotalElements()));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'DIRECTOR', 'COORDINADOR', 'ENFERMERO', 'FISIO', 'TO', 'TS', 'PSICO', 'TAS')")
    public ResponseEntity<ApiDataResponse<UserAllergyResponseDTO>> create(
            @Valid @RequestBody UserAllergyRequestDTO dto) {
        UserAllergyResponseDTO created = userAllergyService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiDataResponse<>(
                        "Alergia creada exitosamente",
                        created,
                        HttpStatus.CREATED.value()));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'DIRECTOR', 'COORDINADOR', 'ENFERMERO', 'FISIO', 'TO', 'TS', 'PSICO', 'TAS')")
    public ResponseEntity<ApiDataResponse<UserAllergyResponseDTO>> update(
            @PathVariable Integer id,
            @Valid @RequestBody UserAllergyUpdateDTO dto) {
        UserAllergyResponseDTO updated = userAllergyService.update(id, dto)
                .orElseThrow(() -> new ResourceNotFoundException("UserAllergy", "id", id));
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Alergia actualizada",
                updated,
                HttpStatus.OK.value()));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'DIRECTOR')")
    public ResponseEntity<ApiDataResponse<UserAllergyResponseDTO>> delete(@PathVariable Integer id) {
        UserAllergyResponseDTO deleted = userAllergyService.delete(id)
                .orElseThrow(() -> new ResourceNotFoundException("UserAllergy", "id", id));
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Alergia eliminada",
                deleted,
                HttpStatus.OK.value()));
    }

}
