package com.centros_sass.app.controller.bathroom;

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

import com.centros_sass.app.dto.bathroom.BathroomTurnRequestDTO;
import com.centros_sass.app.dto.bathroom.BathroomTurnResponseDTO;
import com.centros_sass.app.dto.bathroom.BathroomTurnUpdateDTO;
import com.centros_sass.app.exception.ResourceNotFoundException;
import com.centros_sass.app.generic.ApiDataResponse;
import com.centros_sass.app.service.BathroomTurnService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/bathroom-turns")
@RequiredArgsConstructor
public class BathroomTurnController {

    private final BathroomTurnService bathroomTurnService;

    @GetMapping
    public ResponseEntity<ApiDataResponse<List<BathroomTurnResponseDTO>>> findAll(
            @PageableDefault(size = 20) Pageable pageable) {
        Page<BathroomTurnResponseDTO> page = bathroomTurnService.findAll(pageable);
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Turnos de baño encontrados",
                page.getContent(),
                HttpStatus.OK.value(),
                (int) page.getTotalElements()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiDataResponse<BathroomTurnResponseDTO>> findById(@PathVariable Integer id) {
        BathroomTurnResponseDTO turn = bathroomTurnService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("BathroomTurn", "id", id));
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Turno de baño encontrado",
                turn,
                HttpStatus.OK.value()));
    }

    @PostMapping
    public ResponseEntity<ApiDataResponse<BathroomTurnResponseDTO>> create(
            @Valid @RequestBody BathroomTurnRequestDTO dto) {
        BathroomTurnResponseDTO created = bathroomTurnService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiDataResponse<>(
                        "Turno de baño creado exitosamente",
                        created,
                        HttpStatus.CREATED.value()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiDataResponse<BathroomTurnResponseDTO>> update(
            @PathVariable Integer id,
            @Valid @RequestBody BathroomTurnUpdateDTO dto) {
        BathroomTurnResponseDTO updated = bathroomTurnService.update(id, dto)
                .orElseThrow(() -> new ResourceNotFoundException("BathroomTurn", "id", id));
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Turno de baño actualizado",
                updated,
                HttpStatus.OK.value()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiDataResponse<BathroomTurnResponseDTO>> delete(@PathVariable Integer id) {
        BathroomTurnResponseDTO deleted = bathroomTurnService.delete(id)
                .orElseThrow(() -> new ResourceNotFoundException("BathroomTurn", "id", id));
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Turno de baño eliminado",
                deleted,
                HttpStatus.OK.value()));
    }

}
