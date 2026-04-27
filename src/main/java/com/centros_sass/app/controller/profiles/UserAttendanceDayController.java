package com.centros_sass.app.controller.profiles;

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
import org.springframework.web.bind.annotation.RestController;

import com.centros_sass.app.dto.userattendanceday.UserAttendanceDayRequestDTO;
import com.centros_sass.app.dto.userattendanceday.UserAttendanceDayResponseDTO;
import com.centros_sass.app.dto.userattendanceday.UserAttendanceDayUpdateDTO;
import com.centros_sass.app.exception.ResourceNotFoundException;
import com.centros_sass.app.generic.ApiDataResponse;
import com.centros_sass.app.service.UserAttendanceDayService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/user-attendance-days")
@RequiredArgsConstructor
public class UserAttendanceDayController {

    private final UserAttendanceDayService attendanceDayService;

    @GetMapping
    public ResponseEntity<ApiDataResponse<List<UserAttendanceDayResponseDTO>>> findAll() {
        List<UserAttendanceDayResponseDTO> list = attendanceDayService.findAll();
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Planillas de asistencia encontradas",
                list,
                HttpStatus.OK.value(),
                list.size()));
    }

    @GetMapping("/inactive")
    public ResponseEntity<ApiDataResponse<List<UserAttendanceDayResponseDTO>>> findAllInactive() {
        List<UserAttendanceDayResponseDTO> list = attendanceDayService.findAllInactive();
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Planillas de asistencia inactivas",
                list,
                HttpStatus.OK.value(),
                list.size()));
    }

    @GetMapping("/all")
    public ResponseEntity<ApiDataResponse<List<UserAttendanceDayResponseDTO>>> findAllIncludingInactive() {
        List<UserAttendanceDayResponseDTO> list = attendanceDayService.findAllIncludingInactive();
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Todas las planillas de asistencia",
                list,
                HttpStatus.OK.value(),
                list.size()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiDataResponse<UserAttendanceDayResponseDTO>> findById(@PathVariable Integer id) {
        UserAttendanceDayResponseDTO dto = attendanceDayService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("UserAttendanceDay", "id", id));
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Planilla de asistencia encontrada",
                dto,
                HttpStatus.OK.value()));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiDataResponse<List<UserAttendanceDayResponseDTO>>> findByUserId(@PathVariable Integer userId) {
        List<UserAttendanceDayResponseDTO> list = attendanceDayService.findByUserId(userId);
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Planillas de asistencia del usuario",
                list,
                HttpStatus.OK.value(),
                list.size()));
    }

    @PostMapping
    public ResponseEntity<ApiDataResponse<UserAttendanceDayResponseDTO>> create(
            @Valid @RequestBody UserAttendanceDayRequestDTO dto) {
        UserAttendanceDayResponseDTO created = attendanceDayService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiDataResponse<>(
                        "Planilla de asistencia creada",
                        created,
                        HttpStatus.CREATED.value()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiDataResponse<UserAttendanceDayResponseDTO>> update(
            @PathVariable Integer id,
            @Valid @RequestBody UserAttendanceDayUpdateDTO dto) {
        UserAttendanceDayResponseDTO updated = attendanceDayService.update(id, dto)
                .orElseThrow(() -> new ResourceNotFoundException("UserAttendanceDay", "id", id));
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Planilla de asistencia actualizada",
                updated,
                HttpStatus.OK.value()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiDataResponse<UserAttendanceDayResponseDTO>> delete(@PathVariable Integer id) {
        UserAttendanceDayResponseDTO deleted = attendanceDayService.delete(id)
                .orElseThrow(() -> new ResourceNotFoundException("UserAttendanceDay", "id", id));
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Planilla de asistencia eliminada",
                deleted,
                HttpStatus.OK.value()));
    }

}
