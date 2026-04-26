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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.centros_sass.app.dto.user.UserRequestDTO;
import com.centros_sass.app.dto.user.UserResponseDTO;
import com.centros_sass.app.dto.user.UserUpdateDTO;
import com.centros_sass.app.exception.ResourceNotFoundException;
import com.centros_sass.app.generic.ApiDataResponse;
import com.centros_sass.app.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<ApiDataResponse<List<UserResponseDTO>>> findAll(
            @PageableDefault(size = 20) Pageable pageable,
            @RequestParam(required = false) String search
        ) {
        Page<UserResponseDTO> page = userService.findAll(pageable, search);
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Usuarios activos encontrados",
                page.getContent(),
                HttpStatus.OK.value(),
                (int) page.getTotalElements()));
    }

    @GetMapping("/inactive")
    public ResponseEntity<ApiDataResponse<List<UserResponseDTO>>> findAllInactive(
            @PageableDefault(size = 20) Pageable pageable,
            @RequestParam(required = false) String search
        ) {
        Page<UserResponseDTO> page = userService.findAllInactive(pageable, search);
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Usuarios inactivos encontrados",
                page.getContent(),
                HttpStatus.OK.value(),
                (int) page.getTotalElements()));
    }

    @GetMapping("/all")
    public ResponseEntity<ApiDataResponse<List<UserResponseDTO>>> findAllIncludingInactive(
            @PageableDefault(size = 20) Pageable pageable) {
        Page<UserResponseDTO> page = userService.findAllIncludingInactive(pageable);
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Todos los usuarios encontrados",
                page.getContent(),
                HttpStatus.OK.value(),
                (int) page.getTotalElements()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiDataResponse<UserResponseDTO>> findById(@PathVariable Integer id) {
        UserResponseDTO user = userService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Usuario encontrado",
                user,
                HttpStatus.OK.value()));
    }

    @PostMapping
    public ResponseEntity<ApiDataResponse<UserResponseDTO>> create(
            @Valid @RequestBody UserRequestDTO dto) {
        UserResponseDTO created = userService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiDataResponse<>(
                        "Usuario creado exitosamente",
                        created,
                        HttpStatus.CREATED.value()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiDataResponse<UserResponseDTO>> update(
            @PathVariable Integer id,
            @Valid @RequestBody UserUpdateDTO dto) {
        UserResponseDTO updated = userService.update(id, dto)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Usuario actualizado",
                updated,
                HttpStatus.OK.value()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiDataResponse<UserResponseDTO>> delete(@PathVariable Integer id) {
        UserResponseDTO deleted = userService.delete(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Usuario eliminado",
                deleted,
                HttpStatus.OK.value()));
    }

}
