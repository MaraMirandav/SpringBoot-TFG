package com.centros_sass.app.controller.profiles;

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

import com.centros_sass.app.dto.workerschedule.WorkerScheduleRequestDTO;
import com.centros_sass.app.dto.workerschedule.WorkerScheduleResponseDTO;
import com.centros_sass.app.dto.workerschedule.WorkerScheduleUpdateDTO;
import com.centros_sass.app.exception.ResourceNotFoundException;
import com.centros_sass.app.generic.ApiDataResponse;
import com.centros_sass.app.service.WorkerScheduleService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/worker-schedules")
@RequiredArgsConstructor
public class WorkerScheduleController {

    private final WorkerScheduleService workerScheduleService;

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiDataResponse<List<WorkerScheduleResponseDTO>>> findAll(
            @PageableDefault(size = 20) Pageable pageable) {
        Page<WorkerScheduleResponseDTO> page = workerScheduleService.findAll(pageable);
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Horarios encontrados",
                page.getContent(),
                HttpStatus.OK.value(),
                (int) page.getTotalElements()));
    }

    @GetMapping("/inactive")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiDataResponse<List<WorkerScheduleResponseDTO>>> findAllInactive(
            @PageableDefault(size = 20) Pageable pageable) {
        Page<WorkerScheduleResponseDTO> page = workerScheduleService.findAllInactive(pageable);
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Horarios inactivos encontrados",
                page.getContent(),
                HttpStatus.OK.value(),
                (int) page.getTotalElements()));
    }

    @GetMapping("/worker/{workerId}")
    @PreAuthorize("@securityService.isOwnerOrAdmin(#workerId)")
    public ResponseEntity<ApiDataResponse<List<WorkerScheduleResponseDTO>>> findByWorkerId(
            @PathVariable Integer workerId,
            @PageableDefault(size = 20) Pageable pageable) {
        Page<WorkerScheduleResponseDTO> page = workerScheduleService.findByWorkerId(workerId, pageable);
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Horarios del trabajador encontrados",
                page.getContent(),
                HttpStatus.OK.value(),
                (int) page.getTotalElements()));
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiDataResponse<WorkerScheduleResponseDTO>> findById(@PathVariable Integer id) {
        WorkerScheduleResponseDTO schedule = workerScheduleService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("WorkerSchedule", "id", id));
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Horario encontrado",
                schedule,
                HttpStatus.OK.value()));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'DIRECTOR', 'COORDINADOR')")
    public ResponseEntity<ApiDataResponse<WorkerScheduleResponseDTO>> create(
            @Valid @RequestBody WorkerScheduleRequestDTO dto) {
        WorkerScheduleResponseDTO created = workerScheduleService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiDataResponse<>(
                        "Horario creado exitosamente",
                        created,
                        HttpStatus.CREATED.value()));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'DIRECTOR', 'COORDINADOR')")
    public ResponseEntity<ApiDataResponse<WorkerScheduleResponseDTO>> update(
            @PathVariable Integer id,
            @Valid @RequestBody WorkerScheduleUpdateDTO dto) {
        WorkerScheduleResponseDTO updated = workerScheduleService.update(id, dto)
                .orElseThrow(() -> new ResourceNotFoundException("WorkerSchedule", "id", id));
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Horario actualizado",
                updated,
                HttpStatus.OK.value()));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'DIRECTOR')")
    public ResponseEntity<ApiDataResponse<WorkerScheduleResponseDTO>> delete(@PathVariable Integer id) {
        WorkerScheduleResponseDTO deleted = workerScheduleService.delete(id)
                .orElseThrow(() -> new ResourceNotFoundException("WorkerSchedule", "id", id));
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Horario eliminado",
                deleted,
                HttpStatus.OK.value()));
    }

}
