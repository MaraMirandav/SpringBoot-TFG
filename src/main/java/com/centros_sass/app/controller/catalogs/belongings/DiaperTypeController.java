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
import org.springframework.web.bind.annotation.RestController;

import com.centros_sass.app.dto.catalogs.belongings.DiaperTypeRequestDTO;
import com.centros_sass.app.dto.catalogs.belongings.DiaperTypeResponseDTO;
import com.centros_sass.app.dto.catalogs.belongings.DiaperTypeUpdateDTO;
import com.centros_sass.app.exception.ResourceNotFoundException;
import com.centros_sass.app.generic.ApiDataResponse;
import com.centros_sass.app.service.DiaperTypeService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/diaper-types")
@RequiredArgsConstructor
public class DiaperTypeController {

    private final DiaperTypeService diaperTypeService;

    @GetMapping
    public ResponseEntity<ApiDataResponse<List<DiaperTypeResponseDTO>>> findAll(
            @PageableDefault(size = 20) Pageable pageable) {
        Page<DiaperTypeResponseDTO> page = diaperTypeService.findAll(pageable);
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Tipos de pañal encontrados",
                page.getContent(),
                HttpStatus.OK.value(),
                (int) page.getTotalElements()));
    }



    @GetMapping("/{id}")
    public ResponseEntity<ApiDataResponse<DiaperTypeResponseDTO>> findById(@PathVariable Integer id) {
        DiaperTypeResponseDTO type = diaperTypeService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("DiaperType", "id", id));
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Tipo de pañal encontrado",
                type,
                HttpStatus.OK.value()));
    }

    @PostMapping
    public ResponseEntity<ApiDataResponse<DiaperTypeResponseDTO>> create(
            @Valid @RequestBody DiaperTypeRequestDTO dto) {
        DiaperTypeResponseDTO created = diaperTypeService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiDataResponse<>(
                        "Tipo de pañal creado exitosamente",
                        created,
                        HttpStatus.CREATED.value()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiDataResponse<DiaperTypeResponseDTO>> update(
            @PathVariable Integer id,
            @Valid @RequestBody DiaperTypeUpdateDTO dto) {
        DiaperTypeResponseDTO updated = diaperTypeService.update(id, dto)
                .orElseThrow(() -> new ResourceNotFoundException("DiaperType", "id", id));
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Tipo de pañal actualizado",
                updated,
                HttpStatus.OK.value()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiDataResponse<Void>> delete(@PathVariable Integer id) {
        diaperTypeService.delete(id);
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Tipo de pañal eliminada",
                HttpStatus.OK.value()));
    }


}
