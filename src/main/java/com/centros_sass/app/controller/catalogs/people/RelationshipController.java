package com.centros_sass.app.controller.catalogs.people;

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

import com.centros_sass.app.dto.catalogs.people.RelationshipRequestDTO;
import com.centros_sass.app.dto.catalogs.people.RelationshipResponseDTO;
import com.centros_sass.app.dto.catalogs.people.RelationshipUpdateDTO;
import com.centros_sass.app.exception.ResourceNotFoundException;
import com.centros_sass.app.generic.ApiDataResponse;
import com.centros_sass.app.service.RelationshipService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/relationships")
@RequiredArgsConstructor
public class RelationshipController {

    private final RelationshipService relationshipService;

    @GetMapping
    public ResponseEntity<ApiDataResponse<List<RelationshipResponseDTO>>> findAll(
            @PageableDefault(size = 20) Pageable pageable) {
        Page<RelationshipResponseDTO> page = relationshipService.findAll(pageable);
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Relaciones encontradas",
                page.getContent(),
                HttpStatus.OK.value(),
                (int) page.getTotalElements()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiDataResponse<RelationshipResponseDTO>> findById(@PathVariable Integer id) {
        RelationshipResponseDTO relationship = relationshipService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Relationship", "id", id));
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Relación encontrada",
                relationship,
                HttpStatus.OK.value()));
    }

    @PostMapping
    public ResponseEntity<ApiDataResponse<RelationshipResponseDTO>> create(
            @Valid @RequestBody RelationshipRequestDTO dto) {
        RelationshipResponseDTO created = relationshipService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiDataResponse<>(
                        "Relación creada exitosamente",
                        created,
                        HttpStatus.CREATED.value()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiDataResponse<RelationshipResponseDTO>> update(
            @PathVariable Integer id,
            @Valid @RequestBody RelationshipUpdateDTO dto) {
        RelationshipResponseDTO updated = relationshipService.update(id, dto)
                .orElseThrow(() -> new ResourceNotFoundException("Relationship", "id", id));
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Relación actualizada",
                updated,
                HttpStatus.OK.value()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiDataResponse<Void>> delete(@PathVariable Integer id) {
        relationshipService.delete(id);
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Relación eliminada",
                HttpStatus.OK.value()));
    }

}
