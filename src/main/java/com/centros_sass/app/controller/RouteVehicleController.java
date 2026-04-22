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

import com.centros_sass.app.dto.vehicle.RouteVehicleRequestDTO;
import com.centros_sass.app.dto.vehicle.RouteVehicleResponseDTO;
import com.centros_sass.app.dto.vehicle.RouteVehicleUpdateDTO;
import com.centros_sass.app.exception.ResourceNotFoundException;
import com.centros_sass.app.generic.ApiDataResponse;
import com.centros_sass.app.service.RouteVehicleService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/route-vehicles")
@RequiredArgsConstructor
public class RouteVehicleController {

    private final RouteVehicleService routeVehicleService;

    @GetMapping
    public ResponseEntity<ApiDataResponse<List<RouteVehicleResponseDTO>>> findAll(
            @PageableDefault(size = 20) Pageable pageable) {
        Page<RouteVehicleResponseDTO> page = routeVehicleService.findAll(pageable);
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Vehículos activos encontrados",
                page.getContent(),
                HttpStatus.OK.value(),
                (int) page.getTotalElements()));
    }

    @GetMapping("/inactive")
    public ResponseEntity<ApiDataResponse<List<RouteVehicleResponseDTO>>> findAllInactive(
            @PageableDefault(size = 20) Pageable pageable) {
        Page<RouteVehicleResponseDTO> page = routeVehicleService.findAllInactive(pageable);
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Vehículos inactivos encontrados",
                page.getContent(),
                HttpStatus.OK.value(),
                (int) page.getTotalElements()));
    }

    @GetMapping("/all")
    public ResponseEntity<ApiDataResponse<List<RouteVehicleResponseDTO>>> findAllIncludingInactive(
            @PageableDefault(size = 20) Pageable pageable) {
        Page<RouteVehicleResponseDTO> page = routeVehicleService.findAllIncludingInactive(pageable);
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Todos los vehículos encontrados",
                page.getContent(),
                HttpStatus.OK.value(),
                (int) page.getTotalElements()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiDataResponse<RouteVehicleResponseDTO>> findById(@PathVariable Integer id) {
        RouteVehicleResponseDTO vehicle = routeVehicleService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("RouteVehicle", "id", id));
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Vehículo encontrado",
                vehicle,
                HttpStatus.OK.value()));
    }

    @PostMapping
    public ResponseEntity<ApiDataResponse<RouteVehicleResponseDTO>> create(
            @Valid @RequestBody RouteVehicleRequestDTO dto) {
        RouteVehicleResponseDTO created = routeVehicleService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiDataResponse<>(
                        "Vehículo creado exitosamente",
                        created,
                        HttpStatus.CREATED.value()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiDataResponse<RouteVehicleResponseDTO>> update(
            @PathVariable Integer id,
            @Valid @RequestBody RouteVehicleUpdateDTO dto) {
        RouteVehicleResponseDTO updated = routeVehicleService.update(id, dto)
                .orElseThrow(() -> new ResourceNotFoundException("RouteVehicle", "id", id));
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Vehículo actualizado",
                updated,
                HttpStatus.OK.value()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiDataResponse<RouteVehicleResponseDTO>> delete(@PathVariable Integer id) {
        RouteVehicleResponseDTO deleted = routeVehicleService.delete(id)
                .orElseThrow(() -> new ResourceNotFoundException("RouteVehicle", "id", id));
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Vehículo eliminado",
                deleted,
                HttpStatus.OK.value()));
    }

}
