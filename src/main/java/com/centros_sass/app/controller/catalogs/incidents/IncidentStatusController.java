/*
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

import com.centros_sass.app.dto.catalogs.incidents.IncidentStatusRequestDTO;
import com.centros_sass.app.dto.catalogs.incidents.IncidentStatusResponseDTO;
import com.centros_sass.app.dto.catalogs.incidents.IncidentStatusUpdateDTO;
import com.centros_sass.app.exception.ResourceNotFoundException;
import com.centros_sass.app.generic.ApiDataResponse;
import com.centros_sass.app.service.IncidentStatusService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/incident-statuses")
@RequiredArgsConstructor
public class IncidentStatusController {

    private final IncidentStatusService incidentStatusService;

    @GetMapping
    public ResponseEntity<ApiDataResponse<List<IncidentStatusResponseDTO>>> findAll(
            @PageableDefault(size = 20) Pageable pageable) {
        Page<IncidentStatusResponseDTO> page = incidentStatusService.findAll(pageable);
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Estados de incidencia encontradas",
                page.getContent(),
                HttpStatus.OK.value(),
                (int) page.getTotalElements()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiDataResponse<IncidentStatusResponseDTO>> findById(@PathVariable Integer id) {
        IncidentStatusResponseDTO dto = incidentStatusService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("IncidentStatus", "id", id));
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Estado de incidencia encontrada",
                dto,
                HttpStatus.OK.value()));
    }

    @PostMapping
    public ResponseEntity<ApiDataResponse<IncidentStatusResponseDTO>> create(
            @Valid @RequestBody IncidentStatusRequestDTO dto) {
        IncidentStatusResponseDTO created = incidentStatusService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiDataResponse<>(
                        "Estado de incidencia creada exitosamente",
                        created,
                        HttpStatus.CREATED.value()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiDataResponse<IncidentStatusResponseDTO>> update(
            @PathVariable Integer id,
            @Valid @RequestBody IncidentStatusUpdateDTO dto) {
        IncidentStatusResponseDTO updated = incidentStatusService.update(id, dto)
                .orElseThrow(() -> new ResourceNotFoundException("IncidentStatus", "id", id));
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Estado de incidencia actualizada",
                updated,
                HttpStatus.OK.value()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiDataResponse<Void>> delete(@PathVariable Integer id) {
        incidentStatusService.delete(id);
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Estado de incidencia eliminada",
                HttpStatus.OK.value()));
    }

}

*/
