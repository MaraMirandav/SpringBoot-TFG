package com.centros_sass.app.controller.catalogs.belongings;

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

import com.centros_sass.app.dto.catalogs.belongings.ClothingTypeRequestDTO;
import com.centros_sass.app.dto.catalogs.belongings.ClothingTypeResponseDTO;
import com.centros_sass.app.dto.catalogs.belongings.ClothingTypeUpdateDTO;
import com.centros_sass.app.exception.ResourceNotFoundException;
import com.centros_sass.app.generic.ApiDataResponse;
import com.centros_sass.app.service.ClothingTypeService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/clothing-types")
@RequiredArgsConstructor
public class ClothingTypeController {

    private final ClothingTypeService clothingTypeService;

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiDataResponse<List<ClothingTypeResponseDTO>>> findAll(
            @PageableDefault(size = 20) Pageable pageable) {
        Page<ClothingTypeResponseDTO> page = clothingTypeService.findAll(pageable);
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Tipos de prenda encontrados",
                page.getContent(),
                HttpStatus.OK.value(),
                (int) page.getTotalElements()));
    }



    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiDataResponse<ClothingTypeResponseDTO>> findById(@PathVariable Integer id) {
        ClothingTypeResponseDTO type = clothingTypeService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ClothingType", "id", id));
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Tipo de prenda encontrado",
                type,
                HttpStatus.OK.value()));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'DIRECTOR', 'COORDINADOR')")
    public ResponseEntity<ApiDataResponse<ClothingTypeResponseDTO>> create(
            @Valid @RequestBody ClothingTypeRequestDTO dto) {
        ClothingTypeResponseDTO created = clothingTypeService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiDataResponse<>(
                        "Tipo de prenda creado exitosamente",
                        created,
                        HttpStatus.CREATED.value()));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'DIRECTOR', 'COORDINADOR')")
    public ResponseEntity<ApiDataResponse<ClothingTypeResponseDTO>> update(
            @PathVariable Integer id,
            @Valid @RequestBody ClothingTypeUpdateDTO dto) {
        ClothingTypeResponseDTO updated = clothingTypeService.update(id, dto)
                .orElseThrow(() -> new ResourceNotFoundException("ClothingType", "id", id));
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Tipo de prenda actualizado",
                updated,
                HttpStatus.OK.value()));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'DIRECTOR')")
    public ResponseEntity<ApiDataResponse<Void>> delete(@PathVariable Integer id) {
        clothingTypeService.delete(id);
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Tipo de prenda eliminada",
                HttpStatus.OK.value()));
    }


}
