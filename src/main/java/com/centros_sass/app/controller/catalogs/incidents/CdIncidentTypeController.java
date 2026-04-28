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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

import com.centros_sass.app.dto.catalogs.incidents.CdIncidentTypeRequestDTO;
import com.centros_sass.app.dto.catalogs.incidents.CdIncidentTypeResponseDTO;
import com.centros_sass.app.dto.catalogs.incidents.CdIncidentTypeUpdateDTO;
import com.centros_sass.app.exception.ResourceNotFoundException;
import com.centros_sass.app.generic.ApiDataResponse;
import com.centros_sass.app.service.CdIncidentTypeService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/cd-incident-types")
@RequiredArgsConstructor
public class CdIncidentTypeController {

    private final CdIncidentTypeService cdIncidentTypeService;

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiDataResponse<List<CdIncidentTypeResponseDTO>>> findAll(
            @PageableDefault(size = 20) Pageable pageable) {
        Page<CdIncidentTypeResponseDTO> page = cdIncidentTypeService.findAll(pageable);
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Tipos de incidencia del centro encontradas",
                page.getContent(),
                HttpStatus.OK.value(),
                (int) page.getTotalElements()));
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiDataResponse<CdIncidentTypeResponseDTO>> findById(@PathVariable Integer id) {
        CdIncidentTypeResponseDTO dto = cdIncidentTypeService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("CdIncidentType", "id", id));
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Tipo de incidencia del centro encontrada",
                dto,
                HttpStatus.OK.value()));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'DIRECTOR', 'COORDINADOR')")
    public ResponseEntity<ApiDataResponse<CdIncidentTypeResponseDTO>> create(
            @Valid @RequestBody CdIncidentTypeRequestDTO dto) {
        CdIncidentTypeResponseDTO created = cdIncidentTypeService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiDataResponse<>(
                        "Tipo de incidencia del centro creada exitosamente",
                        created,
                        HttpStatus.CREATED.value()));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'DIRECTOR', 'COORDINADOR')")
    public ResponseEntity<ApiDataResponse<CdIncidentTypeResponseDTO>> update(
            @PathVariable Integer id,
            @Valid @RequestBody CdIncidentTypeUpdateDTO dto) {
        CdIncidentTypeResponseDTO updated = cdIncidentTypeService.update(id, dto)
                .orElseThrow(() -> new ResourceNotFoundException("CdIncidentType", "id", id));
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Tipo de incidencia del centro actualizada",
                updated,
                HttpStatus.OK.value()));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'DIRECTOR')")
    public ResponseEntity<ApiDataResponse<Void>> delete(@PathVariable Integer id) {
        cdIncidentTypeService.delete(id);
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Tipo de incidencia del centro eliminada",
                HttpStatus.OK.value()));
    }

}
