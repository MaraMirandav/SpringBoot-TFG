package com.centros_sass.app.controller;

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

import com.centros_sass.app.dto.people.SexRequestDTO;
import com.centros_sass.app.dto.people.SexResponseDTO;
import com.centros_sass.app.dto.people.SexUpdateDTO;
import com.centros_sass.app.exception.ResourceNotFoundException;
import com.centros_sass.app.generic.ApiDataResponse;
import com.centros_sass.app.service.SexService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/sex")
@RequiredArgsConstructor
public class SexController {

    private final SexService sexService;

    @GetMapping
    public ResponseEntity<ApiDataResponse<List<SexResponseDTO>>> findAll(
            @PageableDefault(size = 20) Pageable pageable) {
        Page<SexResponseDTO> page = sexService.findAll(pageable);
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Sexos encontrados",
                page.getContent(),
                HttpStatus.OK.value(),
                (int) page.getTotalElements()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiDataResponse<SexResponseDTO>> findById(@PathVariable Integer id) {
        SexResponseDTO sex = sexService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sex", "id", id));
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Sexo encontrado",
                sex,
                HttpStatus.OK.value()));
    }

    @PostMapping
    public ResponseEntity<ApiDataResponse<SexResponseDTO>> create(
            @Valid @RequestBody SexRequestDTO dto) {
        SexResponseDTO created = sexService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiDataResponse<>(
                        "Sexo creado exitosamente",
                        created,
                        HttpStatus.CREATED.value()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiDataResponse<SexResponseDTO>> update(
            @PathVariable Integer id,
            @Valid @RequestBody SexUpdateDTO dto) {
        SexResponseDTO updated = sexService.update(id, dto)
                .orElseThrow(() -> new ResourceNotFoundException("Sex", "id", id));
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Sexo actualizado",
                updated,
                HttpStatus.OK.value()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiDataResponse<Void>> delete(@PathVariable Integer id) {
        sexService.delete(id);
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Sexo eliminado",
                HttpStatus.OK.value()));
    }

}
