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
import org.springframework.web.bind.annotation.RestController;

import com.centros_sass.app.dto.treatments.UserIllnessRequestDTO;
import com.centros_sass.app.dto.treatments.UserIllnessResponseDTO;
import com.centros_sass.app.dto.treatments.UserIllnessUpdateDTO;
import com.centros_sass.app.exception.ResourceNotFoundException;
import com.centros_sass.app.generic.ApiDataResponse;
import com.centros_sass.app.service.UserIllnessService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/user-illnesses")
@RequiredArgsConstructor
public class UserIllnessController {

    private final UserIllnessService userIllnessService;

    @GetMapping
    public ResponseEntity<ApiDataResponse<List<UserIllnessResponseDTO>>> findAll(
            @PageableDefault(size = 20) Pageable pageable) {
        Page<UserIllnessResponseDTO> page = userIllnessService.findAll(pageable);
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Enfermedades activas encontradas",
                page.getContent(),
                HttpStatus.OK.value(),
                (int) page.getTotalElements()));
    }

    @GetMapping("/inactive")
    public ResponseEntity<ApiDataResponse<List<UserIllnessResponseDTO>>> findAllInactive(
            @PageableDefault(size = 20) Pageable pageable) {
        Page<UserIllnessResponseDTO> page = userIllnessService.findAllInactive(pageable);
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Enfermedades inactivas encontradas",
                page.getContent(),
                HttpStatus.OK.value(),
                (int) page.getTotalElements()));
    }

    @GetMapping("/all")
    public ResponseEntity<ApiDataResponse<List<UserIllnessResponseDTO>>> findAllIncludingInactive(
            @PageableDefault(size = 20) Pageable pageable) {
        Page<UserIllnessResponseDTO> page = userIllnessService.findAllIncludingInactive(pageable);
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Todas las enfermedades encontradas",
                page.getContent(),
                HttpStatus.OK.value(),
                (int) page.getTotalElements()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiDataResponse<UserIllnessResponseDTO>> findById(@PathVariable Integer id) {
        UserIllnessResponseDTO dto = userIllnessService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("UserIllness", "id", id));
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Enfermedad encontrada",
                dto,
                HttpStatus.OK.value()));
    }

    @GetMapping("/user-medical-info/{infoId}")
    public ResponseEntity<ApiDataResponse<List<UserIllnessResponseDTO>>> findByUserMedicalInfo(
            @PathVariable Integer infoId,
            @PageableDefault(size = 20) Pageable pageable) {
        Page<UserIllnessResponseDTO> page = userIllnessService.findByUserMedicalInfoId(infoId, pageable);
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Enfermedades de la ficha médica encontradas",
                page.getContent(),
                HttpStatus.OK.value(),
                (int) page.getTotalElements()));
    }

    @PostMapping
    public ResponseEntity<ApiDataResponse<UserIllnessResponseDTO>> create(
            @Valid @RequestBody UserIllnessRequestDTO dto) {
        UserIllnessResponseDTO created = userIllnessService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiDataResponse<>(
                        "Enfermedad creada exitosamente",
                        created,
                        HttpStatus.CREATED.value()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiDataResponse<UserIllnessResponseDTO>> update(
            @PathVariable Integer id,
            @Valid @RequestBody UserIllnessUpdateDTO dto) {
        UserIllnessResponseDTO updated = userIllnessService.update(id, dto)
                .orElseThrow(() -> new ResourceNotFoundException("UserIllness", "id", id));
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Enfermedad actualizada",
                updated,
                HttpStatus.OK.value()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiDataResponse<UserIllnessResponseDTO>> delete(@PathVariable Integer id) {
        UserIllnessResponseDTO deleted = userIllnessService.delete(id)
                .orElseThrow(() -> new ResourceNotFoundException("UserIllness", "id", id));
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Enfermedad eliminada",
                deleted,
                HttpStatus.OK.value()));
    }

}
