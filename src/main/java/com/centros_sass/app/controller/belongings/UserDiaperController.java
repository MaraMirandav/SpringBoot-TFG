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

import com.centros_sass.app.dto.belongings.UserDiaperRequestDTO;
import com.centros_sass.app.dto.belongings.UserDiaperResponseDTO;
import com.centros_sass.app.dto.belongings.UserDiaperUpdateDTO;
import com.centros_sass.app.exception.ResourceNotFoundException;
import com.centros_sass.app.generic.ApiDataResponse;
import com.centros_sass.app.service.UserDiaperService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/user-diapers")
@RequiredArgsConstructor
public class UserDiaperController {

    private final UserDiaperService userDiaperService;

    @GetMapping
    public ResponseEntity<ApiDataResponse<List<UserDiaperResponseDTO>>> findAll(
            @PageableDefault(size = 20) Pageable pageable) {
        Page<UserDiaperResponseDTO> page = userDiaperService.findAll(pageable);
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Pañales activos encontrados",
                page.getContent(),
                HttpStatus.OK.value(),
                (int) page.getTotalElements()));
    }

    @GetMapping("/inactive")
    public ResponseEntity<ApiDataResponse<List<UserDiaperResponseDTO>>> findAllInactive(
            @PageableDefault(size = 20) Pageable pageable) {
        Page<UserDiaperResponseDTO> page = userDiaperService.findAllInactive(pageable);
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Pañales inactivos encontrados",
                page.getContent(),
                HttpStatus.OK.value(),
                (int) page.getTotalElements()));
    }

    @GetMapping("/all")
    public ResponseEntity<ApiDataResponse<List<UserDiaperResponseDTO>>> findAllIncludingInactive(
            @PageableDefault(size = 20) Pageable pageable) {
        Page<UserDiaperResponseDTO> page = userDiaperService.findAllIncludingInactive(pageable);
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Todos los pañales encontrados",
                page.getContent(),
                HttpStatus.OK.value(),
                (int) page.getTotalElements()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiDataResponse<UserDiaperResponseDTO>> findById(@PathVariable Integer id) {
        UserDiaperResponseDTO diaper = userDiaperService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("UserDiaper", "id", id));
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Pañal encontrado",
                diaper,
                HttpStatus.OK.value()));
    }

    @GetMapping("/size/{sizeId}")
    public ResponseEntity<ApiDataResponse<List<UserDiaperResponseDTO>>> findByDiaperSize(
            @PathVariable Integer sizeId,
            @PageableDefault(size = 20) Pageable pageable) {
        Page<UserDiaperResponseDTO> page = userDiaperService.findByDiaperSizeId(sizeId, pageable);
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Pañales por talla encontrados",
                page.getContent(),
                HttpStatus.OK.value(),
                (int) page.getTotalElements()));
    }

    @GetMapping("/type/{typeId}")
    public ResponseEntity<ApiDataResponse<List<UserDiaperResponseDTO>>> findByDiaperType(
            @PathVariable Integer typeId,
            @PageableDefault(size = 20) Pageable pageable) {
        Page<UserDiaperResponseDTO> page = userDiaperService.findByDiaperTypeId(typeId, pageable);
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Pañales por tipo encontrados",
                page.getContent(),
                HttpStatus.OK.value(),
                (int) page.getTotalElements()));
    }

    @PostMapping
    public ResponseEntity<ApiDataResponse<UserDiaperResponseDTO>> create(
            @Valid @RequestBody UserDiaperRequestDTO dto) {
        UserDiaperResponseDTO created = userDiaperService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiDataResponse<>(
                        "Pañal creado exitosamente",
                        created,
                        HttpStatus.CREATED.value()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiDataResponse<UserDiaperResponseDTO>> update(
            @PathVariable Integer id,
            @Valid @RequestBody UserDiaperUpdateDTO dto) {
        UserDiaperResponseDTO updated = userDiaperService.update(id, dto)
                .orElseThrow(() -> new ResourceNotFoundException("UserDiaper", "id", id));
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Pañal actualizado",
                updated,
                HttpStatus.OK.value()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiDataResponse<UserDiaperResponseDTO>> delete(@PathVariable Integer id) {
        UserDiaperResponseDTO deleted = userDiaperService.delete(id)
                .orElseThrow(() -> new ResourceNotFoundException("UserDiaper", "id", id));
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Pañal eliminado",
                deleted,
                HttpStatus.OK.value()));
    }

}
