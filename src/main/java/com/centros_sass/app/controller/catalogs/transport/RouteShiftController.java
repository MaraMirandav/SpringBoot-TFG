package com.centros_sass.app.controller.catalogs.transport;

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

import com.centros_sass.app.dto.catalogs.transport.RouteShiftRequestDTO;
import com.centros_sass.app.dto.catalogs.transport.RouteShiftResponseDTO;
import com.centros_sass.app.dto.catalogs.transport.RouteShiftUpdateDTO;
import com.centros_sass.app.exception.ResourceNotFoundException;
import com.centros_sass.app.generic.ApiDataResponse;
import com.centros_sass.app.service.RouteShiftService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/route-shifts")
@RequiredArgsConstructor
public class RouteShiftController {

    private final RouteShiftService routeShiftService;

    @GetMapping
    public ResponseEntity<ApiDataResponse<List<RouteShiftResponseDTO>>> findAll(
            @PageableDefault(size = 20) Pageable pageable) {
        Page<RouteShiftResponseDTO> page = routeShiftService.findAll(pageable);
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Turnos de ruta encontradas",
                page.getContent(),
                HttpStatus.OK.value(),
                (int) page.getTotalElements()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiDataResponse<RouteShiftResponseDTO>> findById(@PathVariable Integer id) {
        RouteShiftResponseDTO dto = routeShiftService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("RouteShift", "id", id));
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Turno de ruta encontrada",
                dto,
                HttpStatus.OK.value()));
    }

    @PostMapping
    public ResponseEntity<ApiDataResponse<RouteShiftResponseDTO>> create(
            @Valid @RequestBody RouteShiftRequestDTO dto) {
        RouteShiftResponseDTO created = routeShiftService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiDataResponse<>(
                        "Turno de ruta creada exitosamente",
                        created,
                        HttpStatus.CREATED.value()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiDataResponse<RouteShiftResponseDTO>> update(
            @PathVariable Integer id,
            @Valid @RequestBody RouteShiftUpdateDTO dto) {
        RouteShiftResponseDTO updated = routeShiftService.update(id, dto)
                .orElseThrow(() -> new ResourceNotFoundException("RouteShift", "id", id));
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Turno de ruta actualizada",
                updated,
                HttpStatus.OK.value()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiDataResponse<Void>> delete(@PathVariable Integer id) {
        routeShiftService.delete(id);
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Turno de ruta eliminada",
                HttpStatus.OK.value()));
    }

}
