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

import com.centros_sass.app.dto.incidents.UserIncidentCommentRequestDTO;
import com.centros_sass.app.dto.incidents.UserIncidentCommentResponseDTO;
import com.centros_sass.app.dto.incidents.UserIncidentCommentUpdateDTO;
import com.centros_sass.app.exception.ResourceNotFoundException;
import com.centros_sass.app.generic.ApiDataResponse;
import com.centros_sass.app.service.UserIncidentCommentService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/user-incidents/{incidentId}/comments")
@RequiredArgsConstructor
public class UserIncidentCommentController {

    private final UserIncidentCommentService commentService;

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiDataResponse<List<UserIncidentCommentResponseDTO>>> findByIncidentId(
            @PathVariable Integer incidentId) {
        List<UserIncidentCommentResponseDTO> comments = commentService.findByIncidentId(incidentId);
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Comentarios de la incidencia de usuario encontrados",
                comments,
                HttpStatus.OK.value(),
                comments.size()));
    }

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiDataResponse<UserIncidentCommentResponseDTO>> create(
            @PathVariable Integer incidentId,
            @Valid @RequestBody UserIncidentCommentRequestDTO dto) {
        UserIncidentCommentResponseDTO created = commentService.save(incidentId, dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiDataResponse<>(
                        "Comentario de incidencia de usuario creado exitosamente",
                        created,
                        HttpStatus.CREATED.value()));
    }

    @PutMapping("/{commentId}")
    @PreAuthorize("@securityService.isUserCommentAuthorOrAdmin(#commentId) or hasAnyRole('COORDINADOR')")
    public ResponseEntity<ApiDataResponse<UserIncidentCommentResponseDTO>> update(
            @PathVariable Integer incidentId,
            @PathVariable Integer commentId,
            @Valid @RequestBody UserIncidentCommentUpdateDTO dto) {
        UserIncidentCommentResponseDTO updated = commentService.update(commentId, dto)
                .orElseThrow(() -> new ResourceNotFoundException("UserIncidentComment", "id", commentId));
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Comentario de incidencia de usuario actualizado",
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
                "Comentario de incidencia de usuario eliminado",
                HttpStatus.OK.value()));
    }

}
