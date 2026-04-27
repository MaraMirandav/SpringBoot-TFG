package com.centros_sass.app.dto.incidents;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CenterIncidentUpdateDTO {

    private Integer reportedById;

    @Size(max = 1000, message = "El comentario no puede exceder los 1000 caracteres")
    private String comment;

    private Integer incidentStatusId;

    private Integer cdIncidentTypeId;

    private Integer cdSignificanceTypeId;

    private Boolean isActive;

}
