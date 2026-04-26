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
import org.springframework.web.bind.annotation.RestController;

import com.centros_sass.app.dto.catalogs.belongings.ReturnReasonRequestDTO;
import com.centros_sass.app.dto.catalogs.belongings.ReturnReasonResponseDTO;
import com.centros_sass.app.dto.catalogs.belongings.ReturnReasonUpdateDTO;
import com.centros_sass.app.exception.ResourceNotFoundException;
import com.centros_sass.app.generic.ApiDataResponse;
import com.centros_sass.app.service.ReturnReasonService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/return-reasons")
@RequiredArgsConstructor
public class ReturnReasonController {

    private final ReturnReasonService returnReasonService;

    @GetMapping
    public ResponseEntity<ApiDataResponse<List<ReturnReasonResponseDTO>>> findAll(
            @PageableDefault(size = 20) Pageable pageable) {
        Page<ReturnReasonResponseDTO> page = returnReasonService.findAll(pageable);
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Razones de devolución encontradas",
                page.getContent(),
                HttpStatus.OK.value(),
                (int) page.getTotalElements()));
    }



    @GetMapping("/{id}")
    public ResponseEntity<ApiDataResponse<ReturnReasonResponseDTO>> findById(@PathVariable Integer id) {
        ReturnReasonResponseDTO reason = returnReasonService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ReturnReason", "id", id));
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Razón de devolución encontrada",
                reason,
                HttpStatus.OK.value()));
    }

    @PostMapping
    public ResponseEntity<ApiDataResponse<ReturnReasonResponseDTO>> create(
            @Valid @RequestBody ReturnReasonRequestDTO dto) {
        ReturnReasonResponseDTO created = returnReasonService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiDataResponse<>(
                        "Razón de devolución creada exitosamente",
                        created,
                        HttpStatus.CREATED.value()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiDataResponse<ReturnReasonResponseDTO>> update(
            @PathVariable Integer id,
            @Valid @RequestBody ReturnReasonUpdateDTO dto) {
        ReturnReasonResponseDTO updated = returnReasonService.update(id, dto)
                .orElseThrow(() -> new ResourceNotFoundException("ReturnReason", "id", id));
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Razón de devolución actualizada",
                updated,
                HttpStatus.OK.value()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiDataResponse<Void>> delete(@PathVariable Integer id) {
        returnReasonService.delete(id);
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Razón de devolución eliminada",
                HttpStatus.OK.value()));
    }


}
