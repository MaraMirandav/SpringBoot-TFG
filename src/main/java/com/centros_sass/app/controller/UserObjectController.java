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

import com.centros_sass.app.dto.belongings.UserObjectRequestDTO;
import com.centros_sass.app.dto.belongings.UserObjectResponseDTO;
import com.centros_sass.app.dto.belongings.UserObjectUpdateDTO;
import com.centros_sass.app.exception.ResourceNotFoundException;
import com.centros_sass.app.generic.ApiDataResponse;
import com.centros_sass.app.service.UserObjectService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/user-objects")
@RequiredArgsConstructor
public class UserObjectController {

    private final UserObjectService userObjectService;

    @GetMapping
    public ResponseEntity<ApiDataResponse<List<UserObjectResponseDTO>>> findAll(
            @PageableDefault(size = 20) Pageable pageable) {
        Page<UserObjectResponseDTO> page = userObjectService.findAll(pageable);
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Objetos activos encontrados",
                page.getContent(),
                HttpStatus.OK.value(),
                (int) page.getTotalElements()));
    }

    @GetMapping("/inactive")
    public ResponseEntity<ApiDataResponse<List<UserObjectResponseDTO>>> findAllInactive(
            @PageableDefault(size = 20) Pageable pageable) {
        Page<UserObjectResponseDTO> page = userObjectService.findAllInactive(pageable);
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Objetos inactivos encontrados",
                page.getContent(),
                HttpStatus.OK.value(),
                (int) page.getTotalElements()));
    }

    @GetMapping("/all")
    public ResponseEntity<ApiDataResponse<List<UserObjectResponseDTO>>> findAllIncludingInactive(
            @PageableDefault(size = 20) Pageable pageable) {
        Page<UserObjectResponseDTO> page = userObjectService.findAllIncludingInactive(pageable);
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Todos los objetos encontrados",
                page.getContent(),
                HttpStatus.OK.value(),
                (int) page.getTotalElements()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiDataResponse<UserObjectResponseDTO>> findById(@PathVariable Integer id) {
        UserObjectResponseDTO obj = userObjectService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("UserObject", "id", id));
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Objeto encontrado",
                obj,
                HttpStatus.OK.value()));
    }

    @GetMapping("/type/{typeId}")
    public ResponseEntity<ApiDataResponse<List<UserObjectResponseDTO>>> findByObjectType(
            @PathVariable Integer typeId,
            @PageableDefault(size = 20) Pageable pageable) {
        Page<UserObjectResponseDTO> page = userObjectService.findByObjectTypeId(typeId, pageable);
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Objetos por tipo encontrados",
                page.getContent(),
                HttpStatus.OK.value(),
                (int) page.getTotalElements()));
    }

    @GetMapping("/condition/{conditionId}")
    public ResponseEntity<ApiDataResponse<List<UserObjectResponseDTO>>> findByItemCondition(
            @PathVariable Integer conditionId,
            @PageableDefault(size = 20) Pageable pageable) {
        Page<UserObjectResponseDTO> page = userObjectService.findByItemConditionId(conditionId, pageable);
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Objetos por condición encontrados",
                page.getContent(),
                HttpStatus.OK.value(),
                (int) page.getTotalElements()));
    }

    @PostMapping
    public ResponseEntity<ApiDataResponse<UserObjectResponseDTO>> create(
            @Valid @RequestBody UserObjectRequestDTO dto) {
        UserObjectResponseDTO created = userObjectService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiDataResponse<>(
                        "Objeto creado exitosamente",
                        created,
                        HttpStatus.CREATED.value()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiDataResponse<UserObjectResponseDTO>> update(
            @PathVariable Integer id,
            @Valid @RequestBody UserObjectUpdateDTO dto) {
        UserObjectResponseDTO updated = userObjectService.update(id, dto)
                .orElseThrow(() -> new ResourceNotFoundException("UserObject", "id", id));
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Objeto actualizado",
                updated,
                HttpStatus.OK.value()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiDataResponse<UserObjectResponseDTO>> delete(@PathVariable Integer id) {
        UserObjectResponseDTO deleted = userObjectService.delete(id)
                .orElseThrow(() -> new ResourceNotFoundException("UserObject", "id", id));
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Objeto eliminado",
                deleted,
                HttpStatus.OK.value()));
    }

}
