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

import com.centros_sass.app.dto.catalogs.belongings.ObjectTypeRequestDTO;
import com.centros_sass.app.dto.catalogs.belongings.ObjectTypeResponseDTO;
import com.centros_sass.app.dto.catalogs.belongings.ObjectTypeUpdateDTO;
import com.centros_sass.app.exception.ResourceNotFoundException;
import com.centros_sass.app.generic.ApiDataResponse;
import com.centros_sass.app.service.ObjectTypeService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/object-types")
@RequiredArgsConstructor
public class ObjectTypeController {

    private final ObjectTypeService objectTypeService;

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiDataResponse<List<ObjectTypeResponseDTO>>> findAll(
            @PageableDefault(size = 20) Pageable pageable) {
        Page<ObjectTypeResponseDTO> page = objectTypeService.findAll(pageable);
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Tipos de objeto encontrados",
                page.getContent(),
                HttpStatus.OK.value(),
                (int) page.getTotalElements()));
    }



    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiDataResponse<ObjectTypeResponseDTO>> findById(@PathVariable Integer id) {
        ObjectTypeResponseDTO type = objectTypeService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ObjectType", "id", id));
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Tipo de objeto encontrado",
                type,
                HttpStatus.OK.value()));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'DIRECTOR', 'COORDINADOR')")
    public ResponseEntity<ApiDataResponse<ObjectTypeResponseDTO>> create(
            @Valid @RequestBody ObjectTypeRequestDTO dto) {
        ObjectTypeResponseDTO created = objectTypeService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiDataResponse<>(
                        "Tipo de objeto creado exitosamente",
                        created,
                        HttpStatus.CREATED.value()));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'DIRECTOR', 'COORDINADOR')")
    public ResponseEntity<ApiDataResponse<ObjectTypeResponseDTO>> update(
            @PathVariable Integer id,
            @Valid @RequestBody ObjectTypeUpdateDTO dto) {
        ObjectTypeResponseDTO updated = objectTypeService.update(id, dto)
                .orElseThrow(() -> new ResourceNotFoundException("ObjectType", "id", id));
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Tipo de objeto actualizado",
                updated,
                HttpStatus.OK.value()));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'DIRECTOR')")
    public ResponseEntity<ApiDataResponse<Void>> delete(@PathVariable Integer id) {
        objectTypeService.delete(id);
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Tipo de objeto eliminada",
                HttpStatus.OK.value()));
    }


}
