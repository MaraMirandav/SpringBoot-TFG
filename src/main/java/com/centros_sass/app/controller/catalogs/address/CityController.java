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

import com.centros_sass.app.dto.catalogs.address.CityRequestDTO;
import com.centros_sass.app.dto.catalogs.address.CityResponseDTO;
import com.centros_sass.app.dto.catalogs.address.CityUpdateDTO;
import com.centros_sass.app.exception.ResourceNotFoundException;
import com.centros_sass.app.generic.ApiDataResponse;
import com.centros_sass.app.service.CityService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/cities")
@RequiredArgsConstructor
public class CityController {

    private final CityService cityService;

    @GetMapping
    public ResponseEntity<ApiDataResponse<List<CityResponseDTO>>> findAll(
            @PageableDefault(size = 20) Pageable pageable) {
        Page<CityResponseDTO> page = cityService.findAll(pageable);
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Ciudades encontradas",
                page.getContent(),
                HttpStatus.OK.value(),
                (int) page.getTotalElements()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiDataResponse<CityResponseDTO>> findById(@PathVariable Integer id) {
        CityResponseDTO dto = cityService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("City", "id", id));
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Ciudad encontrada",
                dto,
                HttpStatus.OK.value()));
    }

    @PostMapping
    public ResponseEntity<ApiDataResponse<CityResponseDTO>> create(
            @Valid @RequestBody CityRequestDTO dto) {
        CityResponseDTO created = cityService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiDataResponse<>(
                        "Ciudad creada exitosamente",
                        created,
                        HttpStatus.CREATED.value()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiDataResponse<CityResponseDTO>> update(
            @PathVariable Integer id,
            @Valid @RequestBody CityUpdateDTO dto) {
        CityResponseDTO updated = cityService.update(id, dto)
                .orElseThrow(() -> new ResourceNotFoundException("City", "id", id));
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Ciudad actualizada",
                updated,
                HttpStatus.OK.value()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiDataResponse<Void>> delete(@PathVariable Integer id) {
        cityService.delete(id);
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Ciudad eliminada",
                HttpStatus.OK.value()));
    }

}
