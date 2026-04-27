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

import com.centros_sass.app.dto.workerschedulerecord.WorkerScheduleRecordResponseDTO;
import com.centros_sass.app.dto.workerschedulerecord.WorkerScheduleRecordUpdateDTO;
import com.centros_sass.app.exception.ResourceNotFoundException;
import com.centros_sass.app.generic.ApiDataResponse;
import com.centros_sass.app.service.WorkerScheduleRecordService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/worker-schedule-records")
@RequiredArgsConstructor
public class WorkerScheduleRecordController {

    private final WorkerScheduleRecordService recordService;

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiDataResponse<List<WorkerScheduleRecordResponseDTO>>> findAll(
            @PageableDefault(size = 20) Pageable pageable) {
        Page<WorkerScheduleRecordResponseDTO> page = recordService.findAll(pageable);
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Fichajes encontrados",
                page.getContent(),
                HttpStatus.OK.value(),
                (int) page.getTotalElements()));
    }

    @GetMapping("/inactive")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiDataResponse<List<WorkerScheduleRecordResponseDTO>>> findAllInactive(
            @PageableDefault(size = 20) Pageable pageable) {
        Page<WorkerScheduleRecordResponseDTO> page = recordService.findAllInactive(pageable);
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Fichajes inactivos encontrados",
                page.getContent(),
                HttpStatus.OK.value(),
                (int) page.getTotalElements()));
    }

    @GetMapping("/worker/{workerId}")
    @PreAuthorize("@securityService.isOwnerOrAdmin(#workerId)")
    public ResponseEntity<ApiDataResponse<List<WorkerScheduleRecordResponseDTO>>> findByWorkerId(
            @PathVariable Integer workerId,
            @PageableDefault(size = 20) Pageable pageable) {
        Page<WorkerScheduleRecordResponseDTO> page = recordService.findByWorkerId(workerId, pageable);
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Fichajes del trabajador encontrados",
                page.getContent(),
                HttpStatus.OK.value(),
                (int) page.getTotalElements()));
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiDataResponse<WorkerScheduleRecordResponseDTO>> findById(@PathVariable Integer id) {
        WorkerScheduleRecordResponseDTO record = recordService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("WorkerScheduleRecord", "id", id));
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Fichaje encontrado",
                record,
                HttpStatus.OK.value()));
    }

    @PostMapping("/clock-in/today")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiDataResponse<WorkerScheduleRecordResponseDTO>> clockInToday() {
        WorkerScheduleRecordResponseDTO created = recordService.clockInToday();
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiDataResponse<>(
                        "Entrada registrada exitosamente",
                        created,
                        HttpStatus.CREATED.value()));
    }

    @PostMapping("/clock-out/today")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiDataResponse<WorkerScheduleRecordResponseDTO>> clockOutToday() {
        WorkerScheduleRecordResponseDTO updated = recordService.clockOutToday()
                .orElseThrow(() -> new ResourceNotFoundException("Fichaje abierto", "worker", "actual"));
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Salida registrada exitosamente",
                updated,
                HttpStatus.OK.value()));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'DIRECTOR', 'COORDINADOR')")
    public ResponseEntity<ApiDataResponse<WorkerScheduleRecordResponseDTO>> update(
            @PathVariable Integer id,
            @Valid @RequestBody WorkerScheduleRecordUpdateDTO dto) {
        WorkerScheduleRecordResponseDTO updated = recordService.update(id, dto)
                .orElseThrow(() -> new ResourceNotFoundException("WorkerScheduleRecord", "id", id));
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Fichaje actualizado exitosamente",
                updated,
                HttpStatus.OK.value()));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'DIRECTOR')")
    public ResponseEntity<ApiDataResponse<WorkerScheduleRecordResponseDTO>> delete(@PathVariable Integer id) {
        WorkerScheduleRecordResponseDTO deleted = recordService.delete(id)
                .orElseThrow(() -> new ResourceNotFoundException("WorkerScheduleRecord", "id", id));
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Fichaje eliminado",
                deleted,
                HttpStatus.OK.value()));
    }

    @GetMapping("/worker/{workerId}/active")
    @PreAuthorize("@securityService.isOwnerOrAdmin(#workerId)")
    public ResponseEntity<ApiDataResponse<List<WorkerScheduleRecordResponseDTO>>> findActiveClockIns(
            @PathVariable Integer workerId) {
        List<WorkerScheduleRecordResponseDTO> active = recordService.findActiveClockIns(workerId);
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Fichajes abiertos del trabajador",
                active,
                HttpStatus.OK.value(),
                active.size()));
    }

}
