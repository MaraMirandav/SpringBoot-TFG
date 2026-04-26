package com.centros_sass.app.controller.catalogs.address;

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

import com.centros_sass.app.dto.catalogs.address.RegionRequestDTO;
import com.centros_sass.app.dto.catalogs.address.RegionResponseDTO;
import com.centros_sass.app.dto.catalogs.address.RegionUpdateDTO;
import com.centros_sass.app.exception.ResourceNotFoundException;
import com.centros_sass.app.generic.ApiDataResponse;
import com.centros_sass.app.service.RegionService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/regions")
@RequiredArgsConstructor
public class RegionController {

    private final RegionService regionService;

    @GetMapping
    public ResponseEntity<ApiDataResponse<List<RegionResponseDTO>>> findAll(
            @PageableDefault(size = 20) Pageable pageable) {
        Page<RegionResponseDTO> page = regionService.findAll(pageable);
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Regiones encontradas",
                page.getContent(),
                HttpStatus.OK.value(),
                (int) page.getTotalElements()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiDataResponse<RegionResponseDTO>> findById(@PathVariable Integer id) {
        RegionResponseDTO dto = regionService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Region", "id", id));
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Región encontrada",
                dto,
                HttpStatus.OK.value()));
    }

    @PostMapping
    public ResponseEntity<ApiDataResponse<RegionResponseDTO>> create(
            @Valid @RequestBody RegionRequestDTO dto) {
        RegionResponseDTO created = regionService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiDataResponse<>(
                        "Región creada exitosamente",
                        created,
                        HttpStatus.CREATED.value()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiDataResponse<RegionResponseDTO>> update(
            @PathVariable Integer id,
            @Valid @RequestBody RegionUpdateDTO dto) {
        RegionResponseDTO updated = regionService.update(id, dto)
                .orElseThrow(() -> new ResourceNotFoundException("Region", "id", id));
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Región actualizada",
                updated,
                HttpStatus.OK.value()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiDataResponse<Void>> delete(@PathVariable Integer id) {
        regionService.delete(id);
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Región eliminada",
                HttpStatus.OK.value()));
    }

}
