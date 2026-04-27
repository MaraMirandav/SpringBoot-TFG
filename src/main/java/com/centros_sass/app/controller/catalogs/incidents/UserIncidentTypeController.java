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

import com.centros_sass.app.dto.catalogs.incidents.UserIncidentTypeRequestDTO;
import com.centros_sass.app.dto.catalogs.incidents.UserIncidentTypeResponseDTO;
import com.centros_sass.app.dto.catalogs.incidents.UserIncidentTypeUpdateDTO;
import com.centros_sass.app.exception.ResourceNotFoundException;
import com.centros_sass.app.generic.ApiDataResponse;
import com.centros_sass.app.service.UserIncidentTypeService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/user-incident-types")
@RequiredArgsConstructor
public class UserIncidentTypeController {

    private final UserIncidentTypeService userIncidentTypeService;

    @GetMapping
    public ResponseEntity<ApiDataResponse<List<UserIncidentTypeResponseDTO>>> findAll(
            @PageableDefault(size = 20) Pageable pageable) {
        Page<UserIncidentTypeResponseDTO> page = userIncidentTypeService.findAll(pageable);
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Tipos de incidencia de usuario encontradas",
                page.getContent(),
                HttpStatus.OK.value(),
                (int) page.getTotalElements()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiDataResponse<UserIncidentTypeResponseDTO>> findById(@PathVariable Integer id) {
        UserIncidentTypeResponseDTO dto = userIncidentTypeService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("UserIncidentType", "id", id));
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Tipo de incidencia de usuario encontrada",
                dto,
                HttpStatus.OK.value()));
    }

    @PostMapping
    public ResponseEntity<ApiDataResponse<UserIncidentTypeResponseDTO>> create(
            @Valid @RequestBody UserIncidentTypeRequestDTO dto) {
        UserIncidentTypeResponseDTO created = userIncidentTypeService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiDataResponse<>(
                        "Tipo de incidencia de usuario creada exitosamente",
                        created,
                        HttpStatus.CREATED.value()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiDataResponse<UserIncidentTypeResponseDTO>> update(
            @PathVariable Integer id,
            @Valid @RequestBody UserIncidentTypeUpdateDTO dto) {
        UserIncidentTypeResponseDTO updated = userIncidentTypeService.update(id, dto)
                .orElseThrow(() -> new ResourceNotFoundException("UserIncidentType", "id", id));
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Tipo de incidencia de usuario actualizada",
                updated,
                HttpStatus.OK.value()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiDataResponse<Void>> delete(@PathVariable Integer id) {
        userIncidentTypeService.delete(id);
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Tipo de incidencia de usuario eliminada",
                HttpStatus.OK.value()));
    }

}
