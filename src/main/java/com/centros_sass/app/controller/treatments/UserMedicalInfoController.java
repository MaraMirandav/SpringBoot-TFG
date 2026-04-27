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

import com.centros_sass.app.dto.treatments.UserMedicalInfoRequestDTO;
import com.centros_sass.app.dto.treatments.UserMedicalInfoResponseDTO;
import com.centros_sass.app.dto.treatments.UserMedicalInfoUpdateDTO;
import com.centros_sass.app.exception.ResourceNotFoundException;
import com.centros_sass.app.generic.ApiDataResponse;
import com.centros_sass.app.service.UserMedicalInfoService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/user-medical-infos")
@RequiredArgsConstructor
public class UserMedicalInfoController {

    private final UserMedicalInfoService userMedicalInfoService;

    @GetMapping
    public ResponseEntity<ApiDataResponse<List<UserMedicalInfoResponseDTO>>> findAll(
            @PageableDefault(size = 20) Pageable pageable) {
        Page<UserMedicalInfoResponseDTO> page = userMedicalInfoService.findAll(pageable);
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Fichas médicas activas encontradas",
                page.getContent(),
                HttpStatus.OK.value(),
                (int) page.getTotalElements()));
    }

    @GetMapping("/inactive")
    public ResponseEntity<ApiDataResponse<List<UserMedicalInfoResponseDTO>>> findAllInactive(
            @PageableDefault(size = 20) Pageable pageable) {
        Page<UserMedicalInfoResponseDTO> page = userMedicalInfoService.findAllInactive(pageable);
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Fichas médicas inactivas encontradas",
                page.getContent(),
                HttpStatus.OK.value(),
                (int) page.getTotalElements()));
    }

    @GetMapping("/all")
    public ResponseEntity<ApiDataResponse<List<UserMedicalInfoResponseDTO>>> findAllIncludingInactive(
            @PageableDefault(size = 20) Pageable pageable) {
        Page<UserMedicalInfoResponseDTO> page = userMedicalInfoService.findAllIncludingInactive(pageable);
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Todas las fichas médicas encontradas",
                page.getContent(),
                HttpStatus.OK.value(),
                (int) page.getTotalElements()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiDataResponse<UserMedicalInfoResponseDTO>> findById(@PathVariable Integer id) {
        UserMedicalInfoResponseDTO dto = userMedicalInfoService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("UserMedicalInfo", "id", id));
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Ficha médica encontrada",
                dto,
                HttpStatus.OK.value()));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiDataResponse<UserMedicalInfoResponseDTO>> findByUser(@PathVariable Integer userId) {
        UserMedicalInfoResponseDTO dto = userMedicalInfoService.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("UserMedicalInfo", "userId", userId));
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Ficha médica del usuario encontrada",
                dto,
                HttpStatus.OK.value()));
    }

    @PostMapping
    public ResponseEntity<ApiDataResponse<UserMedicalInfoResponseDTO>> create(
            @Valid @RequestBody UserMedicalInfoRequestDTO dto) {
        UserMedicalInfoResponseDTO created = userMedicalInfoService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiDataResponse<>(
                        "Ficha médica creada exitosamente",
                        created,
                        HttpStatus.CREATED.value()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiDataResponse<UserMedicalInfoResponseDTO>> update(
            @PathVariable Integer id,
            @Valid @RequestBody UserMedicalInfoUpdateDTO dto) {
        UserMedicalInfoResponseDTO updated = userMedicalInfoService.update(id, dto)
                .orElseThrow(() -> new ResourceNotFoundException("UserMedicalInfo", "id", id));
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Ficha médica actualizada",
                updated,
                HttpStatus.OK.value()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiDataResponse<UserMedicalInfoResponseDTO>> delete(@PathVariable Integer id) {
        UserMedicalInfoResponseDTO deleted = userMedicalInfoService.delete(id)
                .orElseThrow(() -> new ResourceNotFoundException("UserMedicalInfo", "id", id));
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Ficha médica eliminada",
                deleted,
                HttpStatus.OK.value()));
    }

}
