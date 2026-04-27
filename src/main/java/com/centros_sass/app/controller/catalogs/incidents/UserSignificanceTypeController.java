package com.centros_sass.app.controller.catalogs.incidents;

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

import com.centros_sass.app.dto.catalogs.incidents.UserSignificanceTypeRequestDTO;
import com.centros_sass.app.dto.catalogs.incidents.UserSignificanceTypeResponseDTO;
import com.centros_sass.app.dto.catalogs.incidents.UserSignificanceTypeUpdateDTO;
import com.centros_sass.app.exception.ResourceNotFoundException;
import com.centros_sass.app.generic.ApiDataResponse;
import com.centros_sass.app.service.UserSignificanceTypeService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/user-significance-types")
@RequiredArgsConstructor
public class UserSignificanceTypeController {

    private final UserSignificanceTypeService userSignificanceTypeService;

    @GetMapping
    public ResponseEntity<ApiDataResponse<List<UserSignificanceTypeResponseDTO>>> findAll(
            @PageableDefault(size = 20) Pageable pageable) {
        Page<UserSignificanceTypeResponseDTO> page = userSignificanceTypeService.findAll(pageable);
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Tipos de significancia de usuario encontradas",
                page.getContent(),
                HttpStatus.OK.value(),
                (int) page.getTotalElements()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiDataResponse<UserSignificanceTypeResponseDTO>> findById(@PathVariable Integer id) {
        UserSignificanceTypeResponseDTO dto = userSignificanceTypeService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("UserSignificanceType", "id", id));
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Tipo de significancia de usuario encontrada",
                dto,
                HttpStatus.OK.value()));
    }

    @PostMapping
    public ResponseEntity<ApiDataResponse<UserSignificanceTypeResponseDTO>> create(
            @Valid @RequestBody UserSignificanceTypeRequestDTO dto) {
        UserSignificanceTypeResponseDTO created = userSignificanceTypeService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiDataResponse<>(
                        "Tipo de significancia de usuario creada exitosamente",
                        created,
                        HttpStatus.CREATED.value()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiDataResponse<UserSignificanceTypeResponseDTO>> update(
            @PathVariable Integer id,
            @Valid @RequestBody UserSignificanceTypeUpdateDTO dto) {
        UserSignificanceTypeResponseDTO updated = userSignificanceTypeService.update(id, dto)
                .orElseThrow(() -> new ResourceNotFoundException("UserSignificanceType", "id", id));
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Tipo de significancia de usuario actualizada",
                updated,
                HttpStatus.OK.value()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiDataResponse<Void>> delete(@PathVariable Integer id) {
        userSignificanceTypeService.delete(id);
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Tipo de significancia de usuario eliminada",
                HttpStatus.OK.value()));
    }

}
