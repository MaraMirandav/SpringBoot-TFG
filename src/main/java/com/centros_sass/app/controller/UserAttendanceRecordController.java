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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.centros_sass.app.dto.userattendancerecord.UserAttendanceRecordRequestDTO;
import com.centros_sass.app.dto.userattendancerecord.UserAttendanceRecordResponseDTO;
import com.centros_sass.app.exception.ResourceNotFoundException;
import com.centros_sass.app.generic.ApiDataResponse;
import com.centros_sass.app.service.UserAttendanceRecordService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/user-attendance-records")
@RequiredArgsConstructor
public class UserAttendanceRecordController {

    private final UserAttendanceRecordService recordService;

    @GetMapping
    public ResponseEntity<ApiDataResponse<List<UserAttendanceRecordResponseDTO>>> findAll(
            @PageableDefault(size = 20) Pageable pageable) {
        Page<UserAttendanceRecordResponseDTO> page = recordService.findAll(pageable);
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Registros de asistencia encontrados",
                page.getContent(),
                HttpStatus.OK.value(),
                (int) page.getTotalElements()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiDataResponse<UserAttendanceRecordResponseDTO>> findById(@PathVariable Integer id) {
        UserAttendanceRecordResponseDTO record = recordService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("UserAttendanceRecord", "id", id));
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Registro de asistencia encontrado",
                record,
                HttpStatus.OK.value()));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiDataResponse<List<UserAttendanceRecordResponseDTO>>> findByUserId(
            @PathVariable Integer userId,
            @PageableDefault(size = 20) Pageable pageable) {
        Page<UserAttendanceRecordResponseDTO> page = recordService.findByUserId(userId, pageable);
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Registros de asistencia del usuario encontrados",
                page.getContent(),
                HttpStatus.OK.value(),
                (int) page.getTotalElements()));
    }

    @PostMapping
    public ResponseEntity<ApiDataResponse<UserAttendanceRecordResponseDTO>> save(
            @Valid @RequestBody UserAttendanceRecordRequestDTO dto) {
        UserAttendanceRecordResponseDTO created = recordService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiDataResponse<>(
                        "Registro de asistencia creado",
                        created,
                        HttpStatus.CREATED.value()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiDataResponse<UserAttendanceRecordResponseDTO>> delete(@PathVariable Integer id) {
        UserAttendanceRecordResponseDTO deleted = recordService.delete(id)
                .orElseThrow(() -> new ResourceNotFoundException("UserAttendanceRecord", "id", id));
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Registro de asistencia eliminado",
                deleted,
                HttpStatus.OK.value()));
    }

}
