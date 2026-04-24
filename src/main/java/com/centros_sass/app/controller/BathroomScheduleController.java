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

import com.centros_sass.app.dto.bathroom.BathroomScheduleRequestDTO;
import com.centros_sass.app.dto.bathroom.BathroomScheduleResponseDTO;
import com.centros_sass.app.dto.bathroom.BathroomScheduleUpdateDTO;
import com.centros_sass.app.exception.ResourceNotFoundException;
import com.centros_sass.app.generic.ApiDataResponse;
import com.centros_sass.app.service.BathroomScheduleService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/bathroom-schedules")
@RequiredArgsConstructor
public class BathroomScheduleController {

    private final BathroomScheduleService bathroomScheduleService;

    @GetMapping
    public ResponseEntity<ApiDataResponse<List<BathroomScheduleResponseDTO>>> findAll(
            @PageableDefault(size = 20) Pageable pageable) {
        Page<BathroomScheduleResponseDTO> page = bathroomScheduleService.findAll(pageable);
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Registros de higiene activos encontrados",
                page.getContent(),
                HttpStatus.OK.value(),
                (int) page.getTotalElements()));
    }

    @GetMapping("/inactive")
    public ResponseEntity<ApiDataResponse<List<BathroomScheduleResponseDTO>>> findAllInactive(
            @PageableDefault(size = 20) Pageable pageable) {
        Page<BathroomScheduleResponseDTO> page = bathroomScheduleService.findAllInactive(pageable);
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Registros de higiene inactivos encontrados",
                page.getContent(),
                HttpStatus.OK.value(),
                (int) page.getTotalElements()));
    }

    @GetMapping("/all")
    public ResponseEntity<ApiDataResponse<List<BathroomScheduleResponseDTO>>> findAllIncludingInactive(
            @PageableDefault(size = 20) Pageable pageable) {
        Page<BathroomScheduleResponseDTO> page = bathroomScheduleService.findAllIncludingInactive(pageable);
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Todos los registros de higiene encontrados",
                page.getContent(),
                HttpStatus.OK.value(),
                (int) page.getTotalElements()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiDataResponse<BathroomScheduleResponseDTO>> findById(@PathVariable Integer id) {
        BathroomScheduleResponseDTO schedule = bathroomScheduleService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("BathroomSchedule", "id", id));
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Registro de higiene encontrado",
                schedule,
                HttpStatus.OK.value()));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiDataResponse<List<BathroomScheduleResponseDTO>>> findByUser(
            @PathVariable Integer userId,
            @PageableDefault(size = 20) Pageable pageable) {
        Page<BathroomScheduleResponseDTO> page = bathroomScheduleService.findByUserId(userId, pageable);
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Registros de higiene del usuario encontrados",
                page.getContent(),
                HttpStatus.OK.value(),
                (int) page.getTotalElements()));
    }

    @GetMapping("/turn/{turnId}")
    public ResponseEntity<ApiDataResponse<List<BathroomScheduleResponseDTO>>> findByTurn(
            @PathVariable Integer turnId,
            @PageableDefault(size = 20) Pageable pageable) {
        Page<BathroomScheduleResponseDTO> page = bathroomScheduleService.findByTurnId(turnId, pageable);
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Registros de higiene del turno encontrados",
                page.getContent(),
                HttpStatus.OK.value(),
                (int) page.getTotalElements()));
    }

    @GetMapping("/task/{taskId}")
    public ResponseEntity<ApiDataResponse<List<BathroomScheduleResponseDTO>>> findByTask(
            @PathVariable Integer taskId,
            @PageableDefault(size = 20) Pageable pageable) {
        Page<BathroomScheduleResponseDTO> page = bathroomScheduleService.findByTaskId(taskId, pageable);
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Registros de higiene de la tarea encontrados",
                page.getContent(),
                HttpStatus.OK.value(),
                (int) page.getTotalElements()));
    }

    @PostMapping
    public ResponseEntity<ApiDataResponse<BathroomScheduleResponseDTO>> create(
            @Valid @RequestBody BathroomScheduleRequestDTO dto) {
        BathroomScheduleResponseDTO created = bathroomScheduleService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiDataResponse<>(
                        "Registro de higiene creado exitosamente",
                        created,
                        HttpStatus.CREATED.value()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiDataResponse<BathroomScheduleResponseDTO>> update(
            @PathVariable Integer id,
            @Valid @RequestBody BathroomScheduleUpdateDTO dto) {
        BathroomScheduleResponseDTO updated = bathroomScheduleService.update(id, dto)
                .orElseThrow(() -> new ResourceNotFoundException("BathroomSchedule", "id", id));
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Registro de higiene actualizado",
                updated,
                HttpStatus.OK.value()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiDataResponse<BathroomScheduleResponseDTO>> delete(@PathVariable Integer id) {
        BathroomScheduleResponseDTO deleted = bathroomScheduleService.delete(id)
                .orElseThrow(() -> new ResourceNotFoundException("BathroomSchedule", "id", id));
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Registro de higiene eliminado",
                deleted,
                HttpStatus.OK.value()));
    }

}
