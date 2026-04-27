package com.centros_sass.app.controller.catalogs.belongings;

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

import com.centros_sass.app.dto.catalogs.belongings.ItemConditionRequestDTO;
import com.centros_sass.app.dto.catalogs.belongings.ItemConditionResponseDTO;
import com.centros_sass.app.dto.catalogs.belongings.ItemConditionUpdateDTO;
import com.centros_sass.app.exception.ResourceNotFoundException;
import com.centros_sass.app.generic.ApiDataResponse;
import com.centros_sass.app.service.ItemConditionService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/item-conditions")
@RequiredArgsConstructor
public class ItemConditionController {

    private final ItemConditionService itemConditionService;

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiDataResponse<List<ItemConditionResponseDTO>>> findAll(
            @PageableDefault(size = 20) Pageable pageable) {
        Page<ItemConditionResponseDTO> page = itemConditionService.findAll(pageable);
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Condiciones encontradas",
                page.getContent(),
                HttpStatus.OK.value(),
                (int) page.getTotalElements()));
    }



    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiDataResponse<ItemConditionResponseDTO>> findById(@PathVariable Integer id) {
        ItemConditionResponseDTO condition = itemConditionService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ItemCondition", "id", id));
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Condición encontrada",
                condition,
                HttpStatus.OK.value()));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'DIRECTOR', 'COORDINADOR')")
    public ResponseEntity<ApiDataResponse<ItemConditionResponseDTO>> create(
            @Valid @RequestBody ItemConditionRequestDTO dto) {
        ItemConditionResponseDTO created = itemConditionService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiDataResponse<>(
                        "Condición creada exitosamente",
                        created,
                        HttpStatus.CREATED.value()));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'DIRECTOR', 'COORDINADOR')")
    public ResponseEntity<ApiDataResponse<ItemConditionResponseDTO>> update(
            @PathVariable Integer id,
            @Valid @RequestBody ItemConditionUpdateDTO dto) {
        ItemConditionResponseDTO updated = itemConditionService.update(id, dto)
                .orElseThrow(() -> new ResourceNotFoundException("ItemCondition", "id", id));
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Condición actualizada",
                updated,
                HttpStatus.OK.value()));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'DIRECTOR')")
    public ResponseEntity<ApiDataResponse<Void>> delete(@PathVariable Integer id) {
        itemConditionService.delete(id);
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Condición eliminada",
                HttpStatus.OK.value()));
    }


}
