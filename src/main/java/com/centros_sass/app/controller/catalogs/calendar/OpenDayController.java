package com.centros_sass.app.controller.catalogs.calendar;

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

import com.centros_sass.app.dto.catalogs.calendar.OpenDayRequestDTO;
import com.centros_sass.app.dto.catalogs.calendar.OpenDayResponseDTO;
import com.centros_sass.app.dto.catalogs.calendar.OpenDayUpdateDTO;
import com.centros_sass.app.exception.ResourceNotFoundException;
import com.centros_sass.app.generic.ApiDataResponse;
import com.centros_sass.app.service.OpenDayService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/open-days")
@RequiredArgsConstructor
public class OpenDayController {

    private final OpenDayService openDayService;

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiDataResponse<List<OpenDayResponseDTO>>> findAll(
            @PageableDefault(size = 20) Pageable pageable) {
        Page<OpenDayResponseDTO> page = openDayService.findAll(pageable);
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Días abiertos encontrados",
                page.getContent(),
                HttpStatus.OK.value(),
                (int) page.getTotalElements()));
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiDataResponse<OpenDayResponseDTO>> findById(@PathVariable Integer id) {
        OpenDayResponseDTO dto = openDayService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("OpenDay", "id", id));
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Día abierto encontrado",
                dto,
                HttpStatus.OK.value()));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'DIRECTOR', 'COORDINADOR')")
    public ResponseEntity<ApiDataResponse<OpenDayResponseDTO>> create(
            @Valid @RequestBody OpenDayRequestDTO dto) {
        OpenDayResponseDTO created = openDayService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiDataResponse<>(
                        "Día abierto creado exitosamente",
                        created,
                        HttpStatus.CREATED.value()));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'DIRECTOR', 'COORDINADOR')")
    public ResponseEntity<ApiDataResponse<OpenDayResponseDTO>> update(
            @PathVariable Integer id,
            @Valid @RequestBody OpenDayUpdateDTO dto) {
        OpenDayResponseDTO updated = openDayService.update(id, dto)
                .orElseThrow(() -> new ResourceNotFoundException("OpenDay", "id", id));
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Día abierto actualizado",
                updated,
                HttpStatus.OK.value()));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'DIRECTOR')")
    public ResponseEntity<ApiDataResponse<Void>> delete(@PathVariable Integer id) {
        openDayService.delete(id);
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Día abierto eliminado",
                HttpStatus.OK.value()));
    }

}
