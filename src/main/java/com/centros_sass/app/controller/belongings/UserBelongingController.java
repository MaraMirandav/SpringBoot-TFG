package com.centros_sass.app.controller.belongings;

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

import com.centros_sass.app.dto.belongings.UserBelongingRequestDTO;
import com.centros_sass.app.dto.belongings.UserBelongingResponseDTO;
import com.centros_sass.app.dto.belongings.UserBelongingUpdateDTO;
import com.centros_sass.app.exception.ResourceNotFoundException;
import com.centros_sass.app.generic.ApiDataResponse;
import com.centros_sass.app.service.UserBelongingService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/user-belongings")
@RequiredArgsConstructor
public class UserBelongingController {

    private final UserBelongingService userBelongingService;

    @GetMapping
    public ResponseEntity<ApiDataResponse<List<UserBelongingResponseDTO>>> findAll(
            @PageableDefault(size = 20) Pageable pageable) {
        Page<UserBelongingResponseDTO> page = userBelongingService.findAll(pageable);
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Registros de pertenencias activos encontrados",
                page.getContent(),
                HttpStatus.OK.value(),
                (int) page.getTotalElements()));
    }

    @GetMapping("/inactive")
    public ResponseEntity<ApiDataResponse<List<UserBelongingResponseDTO>>> findAllInactive(
            @PageableDefault(size = 20) Pageable pageable) {
        Page<UserBelongingResponseDTO> page = userBelongingService.findAllInactive(pageable);
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Registros de pertenencias inactivos encontrados",
                page.getContent(),
                HttpStatus.OK.value(),
                (int) page.getTotalElements()));
    }

    @GetMapping("/all")
    public ResponseEntity<ApiDataResponse<List<UserBelongingResponseDTO>>> findAllIncludingInactive(
            @PageableDefault(size = 20) Pageable pageable) {
        Page<UserBelongingResponseDTO> page = userBelongingService.findAllIncludingInactive(pageable);
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Todos los registros de pertenencias encontrados",
                page.getContent(),
                HttpStatus.OK.value(),
                (int) page.getTotalElements()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiDataResponse<UserBelongingResponseDTO>> findById(@PathVariable Integer id) {
        UserBelongingResponseDTO belonging = userBelongingService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("UserBelonging", "id", id));
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Registro de pertenencia encontrado",
                belonging,
                HttpStatus.OK.value()));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiDataResponse<List<UserBelongingResponseDTO>>> findByUser(
            @PathVariable Integer userId,
            @PageableDefault(size = 20) Pageable pageable) {
        Page<UserBelongingResponseDTO> page = userBelongingService.findByUserId(userId, pageable);
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Registros de pertenencias del usuario encontrados",
                page.getContent(),
                HttpStatus.OK.value(),
                (int) page.getTotalElements()));
    }

    @GetMapping("/worker/{workerId}")
    public ResponseEntity<ApiDataResponse<List<UserBelongingResponseDTO>>> findByWorker(
            @PathVariable Integer workerId,
            @PageableDefault(size = 20) Pageable pageable) {
        Page<UserBelongingResponseDTO> page = userBelongingService.findByWorkerId(workerId, pageable);
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Registros de pertenencias del trabajador encontrados",
                page.getContent(),
                HttpStatus.OK.value(),
                (int) page.getTotalElements()));
    }

    @PostMapping
    public ResponseEntity<ApiDataResponse<UserBelongingResponseDTO>> create(
            @Valid @RequestBody UserBelongingRequestDTO dto) {
        UserBelongingResponseDTO created = userBelongingService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiDataResponse<>(
                        "Registro de pertenencia creado exitosamente",
                        created,
                        HttpStatus.CREATED.value()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiDataResponse<UserBelongingResponseDTO>> update(
            @PathVariable Integer id,
            @Valid @RequestBody UserBelongingUpdateDTO dto) {
        UserBelongingResponseDTO updated = userBelongingService.update(id, dto)
                .orElseThrow(() -> new ResourceNotFoundException("UserBelonging", "id", id));
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Registro de pertenencia actualizado",
                updated,
                HttpStatus.OK.value()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiDataResponse<UserBelongingResponseDTO>> delete(@PathVariable Integer id) {
        UserBelongingResponseDTO deleted = userBelongingService.delete(id)
                .orElseThrow(() -> new ResourceNotFoundException("UserBelonging", "id", id));
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Registro de pertenencia eliminado",
                deleted,
                HttpStatus.OK.value()));
    }

}
