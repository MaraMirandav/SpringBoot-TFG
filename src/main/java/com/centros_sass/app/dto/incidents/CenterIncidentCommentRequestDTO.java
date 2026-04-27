package com.centros_sass.app.dto.incidents;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CenterIncidentCommentRequestDTO(

    @NotBlank(message = "El comentario es obligatorio")
    @Size(max = 1000, message = "El comentario no puede exceder los 1000 caracteres")
    String comment

) {}
