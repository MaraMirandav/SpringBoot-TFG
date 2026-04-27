package com.centros_sass.app.controller.transport;

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

import com.centros_sass.app.dto.transport.TransportRouteRequestDTO;
import com.centros_sass.app.dto.transport.TransportRouteResponseDTO;
import com.centros_sass.app.dto.transport.TransportRouteUpdateDTO;
import com.centros_sass.app.exception.ResourceNotFoundException;
import com.centros_sass.app.generic.ApiDataResponse;
import com.centros_sass.app.service.TransportRouteService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/transport-routes")
@RequiredArgsConstructor
public class TransportRouteController {

    private final TransportRouteService transportRouteService;

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiDataResponse<List<TransportRouteResponseDTO>>> findAll(
            @PageableDefault(size = 20) Pageable pageable) {
        Page<TransportRouteResponseDTO> page = transportRouteService.findAll(pageable);
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Rutas activas encontradas",
                page.getContent(),
                HttpStatus.OK.value(),
                (int) page.getTotalElements()));
    }

    @GetMapping("/inactive")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiDataResponse<List<TransportRouteResponseDTO>>> findAllInactive(
            @PageableDefault(size = 20) Pageable pageable) {
        Page<TransportRouteResponseDTO> page = transportRouteService.findAllInactive(pageable);
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Rutas inactivas encontradas",
                page.getContent(),
                HttpStatus.OK.value(),
                (int) page.getTotalElements()));
    }

    @GetMapping("/all")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiDataResponse<List<TransportRouteResponseDTO>>> findAllIncludingInactive(
            @PageableDefault(size = 20) Pageable pageable) {
        Page<TransportRouteResponseDTO> page = transportRouteService.findAllIncludingInactive(pageable);
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Todas las rutas encontradas",
                page.getContent(),
                HttpStatus.OK.value(),
                (int) page.getTotalElements()));
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiDataResponse<TransportRouteResponseDTO>> findById(@PathVariable Integer id) {
        TransportRouteResponseDTO route = transportRouteService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("TransportRoute", "id", id));
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Ruta encontrada",
                route,
                HttpStatus.OK.value()));
    }

    @GetMapping("/shift/{shiftId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiDataResponse<List<TransportRouteResponseDTO>>> findByShift(
            @PathVariable Integer shiftId,
            @PageableDefault(size = 20) Pageable pageable) {
        Page<TransportRouteResponseDTO> page = transportRouteService.findByShift(shiftId, pageable);
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Rutas del turno encontradas",
                page.getContent(),
                HttpStatus.OK.value(),
                (int) page.getTotalElements()));
    }

    @GetMapping("/driver/{workerId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiDataResponse<List<TransportRouteResponseDTO>>> findByDriver(
            @PathVariable Integer workerId) {
        List<TransportRouteResponseDTO> routes = transportRouteService.findByDriver(workerId);
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Rutas del conductor encontradas",
                routes,
                HttpStatus.OK.value(),
                routes.size()));
    }

    @GetMapping("/copilot/{workerId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiDataResponse<List<TransportRouteResponseDTO>>> findByCopilot(
            @PathVariable Integer workerId) {
        List<TransportRouteResponseDTO> routes = transportRouteService.findByCopilot(workerId);
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Rutas del copiloto encontradas",
                routes,
                HttpStatus.OK.value(),
                routes.size()));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'DIRECTOR', 'COORDINADOR', 'CONDUCTOR', 'COPILOTO')")
    public ResponseEntity<ApiDataResponse<TransportRouteResponseDTO>> create(
            @Valid @RequestBody TransportRouteRequestDTO dto) {
        TransportRouteResponseDTO created = transportRouteService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiDataResponse<>(
                        "Ruta creada exitosamente",
                        created,
                        HttpStatus.CREATED.value()));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'DIRECTOR', 'COORDINADOR', 'CONDUCTOR', 'COPILOTO')")
    public ResponseEntity<ApiDataResponse<TransportRouteResponseDTO>> update(
            @PathVariable Integer id,
            @Valid @RequestBody TransportRouteUpdateDTO dto) {
        TransportRouteResponseDTO updated = transportRouteService.update(id, dto)
                .orElseThrow(() -> new ResourceNotFoundException("TransportRoute", "id", id));
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Ruta actualizada",
                updated,
                HttpStatus.OK.value()));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'DIRECTOR')")
    public ResponseEntity<ApiDataResponse<TransportRouteResponseDTO>> delete(@PathVariable Integer id) {
        TransportRouteResponseDTO deleted = transportRouteService.delete(id)
                .orElseThrow(() -> new ResourceNotFoundException("TransportRoute", "id", id));
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Ruta eliminada",
                deleted,
                HttpStatus.OK.value()));
    }

}
