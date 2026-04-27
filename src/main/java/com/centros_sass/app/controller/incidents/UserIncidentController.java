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

import com.centros_sass.app.dto.incidents.UserIncidentRequestDTO;
import com.centros_sass.app.dto.incidents.UserIncidentResponseDTO;
import com.centros_sass.app.dto.incidents.UserIncidentUpdateDTO;
import com.centros_sass.app.exception.ResourceNotFoundException;
import com.centros_sass.app.generic.ApiDataResponse;
import com.centros_sass.app.service.UserIncidentService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/user-incidents")
@RequiredArgsConstructor
public class UserIncidentController {

    private final UserIncidentService userIncidentService;

    @GetMapping
    public ResponseEntity<ApiDataResponse<List<UserIncidentResponseDTO>>> findAll(
            @PageableDefault(size = 20) Pageable pageable) {
        Page<UserIncidentResponseDTO> page = userIncidentService.findAll(pageable);
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Incidencias de usuario encontradas",
                page.getContent(),
                HttpStatus.OK.value(),
                (int) page.getTotalElements()));
    }

    @GetMapping("/inactive")
    public ResponseEntity<ApiDataResponse<List<UserIncidentResponseDTO>>> findAllInactive(
            @PageableDefault(size = 20) Pageable pageable) {
        Page<UserIncidentResponseDTO> page = userIncidentService.findAllInactive(pageable);
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Incidencias de usuario inactivas encontradas",
                page.getContent(),
                HttpStatus.OK.value(),
                (int) page.getTotalElements()));
    }

    @GetMapping("/all")
    public ResponseEntity<ApiDataResponse<List<UserIncidentResponseDTO>>> findAllIncludingInactive(
            @PageableDefault(size = 20) Pageable pageable) {
        Page<UserIncidentResponseDTO> page = userIncidentService.findAllIncludingInactive(pageable);
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Todas las incidencias de usuario encontradas",
                page.getContent(),
                HttpStatus.OK.value(),
                (int) page.getTotalElements()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiDataResponse<UserIncidentResponseDTO>> findById(@PathVariable Integer id) {
        UserIncidentResponseDTO dto = userIncidentService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("UserIncident", "id", id));
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Incidencia de usuario encontrada",
                dto,
                HttpStatus.OK.value()));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiDataResponse<List<UserIncidentResponseDTO>>> findByUserId(
            @PathVariable Integer userId,
            @PageableDefault(size = 20) Pageable pageable) {
        Page<UserIncidentResponseDTO> page = userIncidentService.findByUserId(userId, pageable);
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Incidencias de usuario por paciente encontradas",
                page.getContent(),
                HttpStatus.OK.value(),
                (int) page.getTotalElements()));
    }

    @GetMapping("/reported-by/{workerId}")
    public ResponseEntity<ApiDataResponse<List<UserIncidentResponseDTO>>> findByReportedById(
            @PathVariable Integer workerId,
            @PageableDefault(size = 20) Pageable pageable) {
        Page<UserIncidentResponseDTO> page = userIncidentService.findByReportedById(workerId, pageable);
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Incidencias de usuario reportadas por el trabajador encontradas",
                page.getContent(),
                HttpStatus.OK.value(),
                (int) page.getTotalElements()));
    }

    @GetMapping("/status/{statusId}")
    public ResponseEntity<ApiDataResponse<List<UserIncidentResponseDTO>>> findByIncidentStatusId(
            @PathVariable Integer statusId,
            @PageableDefault(size = 20) Pageable pageable) {
        Page<UserIncidentResponseDTO> page = userIncidentService.findByIncidentStatusId(statusId, pageable);
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Incidencias de usuario por estado encontradas",
                page.getContent(),
                HttpStatus.OK.value(),
                (int) page.getTotalElements()));
    }

    @PostMapping
    public ResponseEntity<ApiDataResponse<UserIncidentResponseDTO>> create(
            @Valid @RequestBody UserIncidentRequestDTO dto) {
        UserIncidentResponseDTO created = userIncidentService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiDataResponse<>(
                        "Incidencia de usuario creada exitosamente",
                        created,
                        HttpStatus.CREATED.value()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiDataResponse<UserIncidentResponseDTO>> update(
            @PathVariable Integer id,
            @Valid @RequestBody UserIncidentUpdateDTO dto) {
        UserIncidentResponseDTO updated = userIncidentService.update(id, dto)
                .orElseThrow(() -> new ResourceNotFoundException("UserIncident", "id", id));
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Incidencia de usuario actualizada",
                updated,
                HttpStatus.OK.value()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiDataResponse<UserIncidentResponseDTO>> delete(@PathVariable Integer id) {
        UserIncidentResponseDTO deleted = userIncidentService.delete(id)
                .orElseThrow(() -> new ResourceNotFoundException("UserIncident", "id", id));
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Incidencia de usuario eliminada",
                deleted,
                HttpStatus.OK.value()));
    }

}
