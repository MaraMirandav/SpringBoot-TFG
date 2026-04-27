package com.centros_sass.app.controller.transport;

import java.util.List;

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

import com.centros_sass.app.dto.transport.TransportRouteUserRequestDTO;
import com.centros_sass.app.dto.transport.TransportRouteUserResponseDTO;
import com.centros_sass.app.dto.transport.TransportRouteUserUpdateDTO;
import com.centros_sass.app.exception.ResourceNotFoundException;
import com.centros_sass.app.generic.ApiDataResponse;
import com.centros_sass.app.service.TransportRouteUserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/transport-routes")
@RequiredArgsConstructor
public class TransportRouteUserController {

    private final TransportRouteUserService transportRouteUserService;

    @GetMapping("/{routeId}/passengers")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiDataResponse<List<TransportRouteUserResponseDTO>>> findAllByRoute(
            @PathVariable Integer routeId) {
        List<TransportRouteUserResponseDTO> passengers = transportRouteUserService.findAllByRoute(routeId);
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Pasajeros de la ruta encontrados",
                passengers,
                HttpStatus.OK.value(),
                passengers.size()));
    }

    @GetMapping("/{routeId}/passengers/{userId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiDataResponse<TransportRouteUserResponseDTO>> findByRouteIdAndUserId(
            @PathVariable Integer routeId,
            @PathVariable Integer userId) {
        TransportRouteUserResponseDTO passenger = transportRouteUserService.findByRouteIdAndUserId(routeId, userId)
                .orElseThrow(() -> new ResourceNotFoundException("TransportRouteUser", "routeId/userId", routeId + "/" + userId));
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Pasajero encontrado",
                passenger,
                HttpStatus.OK.value()));
    }

    @PostMapping("/{routeId}/passengers")
    @PreAuthorize("hasAnyRole('ADMIN', 'DIRECTOR', 'COORDINADOR', 'CONDUCTOR', 'COPILOTO')")
    public ResponseEntity<ApiDataResponse<TransportRouteUserResponseDTO>> create(
            @PathVariable Integer routeId,
            @Valid @RequestBody TransportRouteUserRequestDTO dto) {
        TransportRouteUserResponseDTO created = transportRouteUserService.save(routeId, dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiDataResponse<>(
                        "Pasajero asignado a la ruta",
                        created,
                        HttpStatus.CREATED.value()));
    }

    @PutMapping("/{routeId}/passengers/{userId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'DIRECTOR', 'COORDINADOR', 'CONDUCTOR', 'COPILOTO')")
    public ResponseEntity<ApiDataResponse<TransportRouteUserResponseDTO>> update(
            @PathVariable Integer routeId,
            @PathVariable Integer userId,
            @Valid @RequestBody TransportRouteUserUpdateDTO dto) {
        TransportRouteUserResponseDTO updated = transportRouteUserService.update(routeId, userId, dto)
                .orElseThrow(() -> new ResourceNotFoundException("TransportRouteUser", "routeId/userId", routeId + "/" + userId));
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Datos del pasajero actualizados",
                updated,
                HttpStatus.OK.value()));
    }

    @DeleteMapping("/{routeId}/passengers/{userId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'DIRECTOR')")
    public ResponseEntity<ApiDataResponse<Void>> delete(
            @PathVariable Integer routeId,
            @PathVariable Integer userId) {
        transportRouteUserService.delete(routeId, userId);
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Pasajero eliminado de la ruta",
                HttpStatus.OK.value()));
    }

}
