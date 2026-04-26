package com.centros_sass.app.controller.belongings;

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

import com.centros_sass.app.dto.belongings.UserClothingRequestDTO;
import com.centros_sass.app.dto.belongings.UserClothingResponseDTO;
import com.centros_sass.app.dto.belongings.UserClothingUpdateDTO;
import com.centros_sass.app.exception.ResourceNotFoundException;
import com.centros_sass.app.generic.ApiDataResponse;
import com.centros_sass.app.service.UserClothingService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/user-clothings")
@RequiredArgsConstructor
public class UserClothingController {

    private final UserClothingService userClothingService;

    @GetMapping
    public ResponseEntity<ApiDataResponse<List<UserClothingResponseDTO>>> findAll(
            @PageableDefault(size = 20) Pageable pageable) {
        Page<UserClothingResponseDTO> page = userClothingService.findAll(pageable);
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Prendas activas encontradas",
                page.getContent(),
                HttpStatus.OK.value(),
                (int) page.getTotalElements()));
    }

    @GetMapping("/inactive")
    public ResponseEntity<ApiDataResponse<List<UserClothingResponseDTO>>> findAllInactive(
            @PageableDefault(size = 20) Pageable pageable) {
        Page<UserClothingResponseDTO> page = userClothingService.findAllInactive(pageable);
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Prendas inactivas encontradas",
                page.getContent(),
                HttpStatus.OK.value(),
                (int) page.getTotalElements()));
    }

    @GetMapping("/all")
    public ResponseEntity<ApiDataResponse<List<UserClothingResponseDTO>>> findAllIncludingInactive(
            @PageableDefault(size = 20) Pageable pageable) {
        Page<UserClothingResponseDTO> page = userClothingService.findAllIncludingInactive(pageable);
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Todas las prendas encontradas",
                page.getContent(),
                HttpStatus.OK.value(),
                (int) page.getTotalElements()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiDataResponse<UserClothingResponseDTO>> findById(@PathVariable Integer id) {
        UserClothingResponseDTO clothing = userClothingService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("UserClothing", "id", id));
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Prenda encontrada",
                clothing,
                HttpStatus.OK.value()));
    }

    @GetMapping("/type/{typeId}")
    public ResponseEntity<ApiDataResponse<List<UserClothingResponseDTO>>> findByClothingType(
            @PathVariable Integer typeId,
            @PageableDefault(size = 20) Pageable pageable) {
        Page<UserClothingResponseDTO> page = userClothingService.findByClothingTypeId(typeId, pageable);
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Prendas por tipo encontradas",
                page.getContent(),
                HttpStatus.OK.value(),
                (int) page.getTotalElements()));
    }

    @PostMapping
    public ResponseEntity<ApiDataResponse<UserClothingResponseDTO>> create(
            @Valid @RequestBody UserClothingRequestDTO dto) {
        UserClothingResponseDTO created = userClothingService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiDataResponse<>(
                        "Prenda creada exitosamente",
                        created,
                        HttpStatus.CREATED.value()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiDataResponse<UserClothingResponseDTO>> update(
            @PathVariable Integer id,
            @Valid @RequestBody UserClothingUpdateDTO dto) {
        UserClothingResponseDTO updated = userClothingService.update(id, dto)
                .orElseThrow(() -> new ResourceNotFoundException("UserClothing", "id", id));
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Prenda actualizada",
                updated,
                HttpStatus.OK.value()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiDataResponse<UserClothingResponseDTO>> delete(@PathVariable Integer id) {
        UserClothingResponseDTO deleted = userClothingService.delete(id)
                .orElseThrow(() -> new ResourceNotFoundException("UserClothing", "id", id));
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Prenda eliminada",
                deleted,
                HttpStatus.OK.value()));
    }

}
