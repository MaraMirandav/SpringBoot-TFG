package com.centros_sass.app.controller.incidents;

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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

import com.centros_sass.app.dto.incidents.CenterIncidentCommentRequestDTO;
import com.centros_sass.app.dto.incidents.CenterIncidentCommentResponseDTO;
import com.centros_sass.app.dto.incidents.CenterIncidentCommentUpdateDTO;
import com.centros_sass.app.exception.ResourceNotFoundException;
import com.centros_sass.app.generic.ApiDataResponse;
import com.centros_sass.app.service.CenterIncidentCommentService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/center-incidents/{incidentId}/comments")
@RequiredArgsConstructor
public class CenterIncidentCommentController {

    private final CenterIncidentCommentService commentService;

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiDataResponse<List<CenterIncidentCommentResponseDTO>>> findByIncidentId(
            @PathVariable Integer incidentId) {
        List<CenterIncidentCommentResponseDTO> comments = commentService.findByIncidentId(incidentId);
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Comentarios de la incidencia del centro encontrados",
                comments,
                HttpStatus.OK.value(),
                comments.size()));
    }

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiDataResponse<CenterIncidentCommentResponseDTO>> create(
            @PathVariable Integer incidentId,
            @Valid @RequestBody CenterIncidentCommentRequestDTO dto) {
        CenterIncidentCommentResponseDTO created = commentService.save(incidentId, dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiDataResponse<>(
                        "Comentario de incidencia del centro creado exitosamente",
                        created,
                        HttpStatus.CREATED.value()));
    }

    @PutMapping("/{commentId}")
    @PreAuthorize("@securityService.isCenterCommentAuthorOrAdmin(#commentId) or hasAnyRole('COORDINADOR')")
    public ResponseEntity<ApiDataResponse<CenterIncidentCommentResponseDTO>> update(
            @PathVariable Integer incidentId,
            @PathVariable Integer commentId,
            @Valid @RequestBody CenterIncidentCommentUpdateDTO dto) {
        CenterIncidentCommentResponseDTO updated = commentService.update(commentId, dto)
                .orElseThrow(() -> new ResourceNotFoundException("CenterIncidentComment", "id", commentId));
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Comentario de incidencia del centro actualizado",
                updated,
                HttpStatus.OK.value()));
    }

    @DeleteMapping("/{commentId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'DIRECTOR')")
    public ResponseEntity<ApiDataResponse<Void>> delete(
            @PathVariable Integer incidentId,
            @PathVariable Integer commentId) {
        commentService.delete(commentId);
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Comentario de incidencia del centro eliminado",
                HttpStatus.OK.value()));
    }

}
