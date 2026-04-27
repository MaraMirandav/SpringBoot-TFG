package com.centros_sass.app.controller.catalogs.incidents;

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

import com.centros_sass.app.dto.catalogs.incidents.CdSignificanceTypeRequestDTO;
import com.centros_sass.app.dto.catalogs.incidents.CdSignificanceTypeResponseDTO;
import com.centros_sass.app.dto.catalogs.incidents.CdSignificanceTypeUpdateDTO;
import com.centros_sass.app.exception.ResourceNotFoundException;
import com.centros_sass.app.generic.ApiDataResponse;
import com.centros_sass.app.service.CdSignificanceTypeService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/cd-significance-types")
@RequiredArgsConstructor
public class CdSignificanceTypeController {

    private final CdSignificanceTypeService cdSignificanceTypeService;

    @GetMapping
    public ResponseEntity<ApiDataResponse<List<CdSignificanceTypeResponseDTO>>> findAll(
            @PageableDefault(size = 20) Pageable pageable) {
        Page<CdSignificanceTypeResponseDTO> page = cdSignificanceTypeService.findAll(pageable);
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Tipos de significancia del centro encontradas",
                page.getContent(),
                HttpStatus.OK.value(),
                (int) page.getTotalElements()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiDataResponse<CdSignificanceTypeResponseDTO>> findById(@PathVariable Integer id) {
        CdSignificanceTypeResponseDTO dto = cdSignificanceTypeService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("CdSignificanceType", "id", id));
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Tipo de significancia del centro encontrada",
                dto,
                HttpStatus.OK.value()));
    }

    @PostMapping
    public ResponseEntity<ApiDataResponse<CdSignificanceTypeResponseDTO>> create(
            @Valid @RequestBody CdSignificanceTypeRequestDTO dto) {
        CdSignificanceTypeResponseDTO created = cdSignificanceTypeService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiDataResponse<>(
                        "Tipo de significancia del centro creada exitosamente",
                        created,
                        HttpStatus.CREATED.value()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiDataResponse<CdSignificanceTypeResponseDTO>> update(
            @PathVariable Integer id,
            @Valid @RequestBody CdSignificanceTypeUpdateDTO dto) {
        CdSignificanceTypeResponseDTO updated = cdSignificanceTypeService.update(id, dto)
                .orElseThrow(() -> new ResourceNotFoundException("CdSignificanceType", "id", id));
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Tipo de significancia del centro actualizada",
                updated,
                HttpStatus.OK.value()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiDataResponse<Void>> delete(@PathVariable Integer id) {
        cdSignificanceTypeService.delete(id);
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Tipo de significancia del centro eliminada",
                HttpStatus.OK.value()));
    }

}
