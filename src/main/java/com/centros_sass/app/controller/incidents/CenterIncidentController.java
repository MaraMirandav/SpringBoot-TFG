package com.centros_sass.app.controller.incidents;

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

import com.centros_sass.app.dto.incidents.CenterIncidentRequestDTO;
import com.centros_sass.app.dto.incidents.CenterIncidentResponseDTO;
import com.centros_sass.app.dto.incidents.CenterIncidentUpdateDTO;
import com.centros_sass.app.exception.ResourceNotFoundException;
import com.centros_sass.app.generic.ApiDataResponse;
import com.centros_sass.app.service.CenterIncidentService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/center-incidents")
@RequiredArgsConstructor
public class CenterIncidentController {

    private final CenterIncidentService centerIncidentService;

    @GetMapping
    public ResponseEntity<ApiDataResponse<List<CenterIncidentResponseDTO>>> findAll(
            @PageableDefault(size = 20) Pageable pageable) {
        Page<CenterIncidentResponseDTO> page = centerIncidentService.findAll(pageable);
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Incidencias del centro encontradas",
                page.getContent(),
                HttpStatus.OK.value(),
                (int) page.getTotalElements()));
    }

    @GetMapping("/inactive")
    public ResponseEntity<ApiDataResponse<List<CenterIncidentResponseDTO>>> findAllInactive(
            @PageableDefault(size = 20) Pageable pageable) {
        Page<CenterIncidentResponseDTO> page = centerIncidentService.findAllInactive(pageable);
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Incidencias del centro inactivas encontradas",
                page.getContent(),
                HttpStatus.OK.value(),
                (int) page.getTotalElements()));
    }

    @GetMapping("/all")
    public ResponseEntity<ApiDataResponse<List<CenterIncidentResponseDTO>>> findAllIncludingInactive(
            @PageableDefault(size = 20) Pageable pageable) {
        Page<CenterIncidentResponseDTO> page = centerIncidentService.findAllIncludingInactive(pageable);
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Todas las incidencias del centro encontradas",
                page.getContent(),
                HttpStatus.OK.value(),
                (int) page.getTotalElements()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiDataResponse<CenterIncidentResponseDTO>> findById(@PathVariable Integer id) {
        CenterIncidentResponseDTO dto = centerIncidentService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("CenterIncident", "id", id));
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Incidencia del centro encontrada",
                dto,
                HttpStatus.OK.value()));
    }

    @GetMapping("/reported-by/{workerId}")
    public ResponseEntity<ApiDataResponse<List<CenterIncidentResponseDTO>>> findByReportedById(
            @PathVariable Integer workerId,
            @PageableDefault(size = 20) Pageable pageable) {
        Page<CenterIncidentResponseDTO> page = centerIncidentService.findByReportedById(workerId, pageable);
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Incidencias del centro reportadas por el trabajador encontradas",
                page.getContent(),
                HttpStatus.OK.value(),
                (int) page.getTotalElements()));
    }

    @GetMapping("/status/{statusId}")
    public ResponseEntity<ApiDataResponse<List<CenterIncidentResponseDTO>>> findByIncidentStatusId(
            @PathVariable Integer statusId,
            @PageableDefault(size = 20) Pageable pageable) {
        Page<CenterIncidentResponseDTO> page = centerIncidentService.findByIncidentStatusId(statusId, pageable);
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Incidencias del centro por estado encontradas",
                page.getContent(),
                HttpStatus.OK.value(),
                (int) page.getTotalElements()));
    }

    @PostMapping
    public ResponseEntity<ApiDataResponse<CenterIncidentResponseDTO>> create(
            @Valid @RequestBody CenterIncidentRequestDTO dto) {
        CenterIncidentResponseDTO created = centerIncidentService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiDataResponse<>(
                        "Incidencia del centro creada exitosamente",
                        created,
                        HttpStatus.CREATED.value()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiDataResponse<CenterIncidentResponseDTO>> update(
            @PathVariable Integer id,
            @Valid @RequestBody CenterIncidentUpdateDTO dto) {
        CenterIncidentResponseDTO updated = centerIncidentService.update(id, dto)
                .orElseThrow(() -> new ResourceNotFoundException("CenterIncident", "id", id));
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Incidencia del centro actualizada",
                updated,
                HttpStatus.OK.value()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiDataResponse<CenterIncidentResponseDTO>> delete(@PathVariable Integer id) {
        CenterIncidentResponseDTO deleted = centerIncidentService.delete(id)
                .orElseThrow(() -> new ResourceNotFoundException("CenterIncident", "id", id));
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Incidencia del centro eliminada",
                deleted,
                HttpStatus.OK.value()));
    }

}
