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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

import com.centros_sass.app.dto.catalogs.address.ProvinceRequestDTO;
import com.centros_sass.app.dto.catalogs.address.ProvinceResponseDTO;
import com.centros_sass.app.dto.catalogs.address.ProvinceUpdateDTO;
import com.centros_sass.app.exception.ResourceNotFoundException;
import com.centros_sass.app.generic.ApiDataResponse;
import com.centros_sass.app.service.ProvinceService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/provinces")
@RequiredArgsConstructor
public class ProvinceController {

    private final ProvinceService provinceService;

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiDataResponse<List<ProvinceResponseDTO>>> findAll(
            @PageableDefault(size = 20) Pageable pageable) {
        Page<ProvinceResponseDTO> page = provinceService.findAll(pageable);
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Provincias encontradas",
                page.getContent(),
                HttpStatus.OK.value(),
                (int) page.getTotalElements()));
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiDataResponse<ProvinceResponseDTO>> findById(@PathVariable Integer id) {
        ProvinceResponseDTO dto = provinceService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Province", "id", id));
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Provincia encontrada",
                dto,
                HttpStatus.OK.value()));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'DIRECTOR', 'COORDINADOR')")
    public ResponseEntity<ApiDataResponse<ProvinceResponseDTO>> create(
            @Valid @RequestBody ProvinceRequestDTO dto) {
        ProvinceResponseDTO created = provinceService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiDataResponse<>(
                        "Provincia creada exitosamente",
                        created,
                        HttpStatus.CREATED.value()));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'DIRECTOR', 'COORDINADOR')")
    public ResponseEntity<ApiDataResponse<ProvinceResponseDTO>> update(
            @PathVariable Integer id,
            @Valid @RequestBody ProvinceUpdateDTO dto) {
        ProvinceResponseDTO updated = provinceService.update(id, dto)
                .orElseThrow(() -> new ResourceNotFoundException("Province", "id", id));
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Provincia actualizada",
                updated,
                HttpStatus.OK.value()));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'DIRECTOR')")
    public ResponseEntity<ApiDataResponse<Void>> delete(@PathVariable Integer id) {
        provinceService.delete(id);
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Provincia eliminada",
                HttpStatus.OK.value()));
    }

}
