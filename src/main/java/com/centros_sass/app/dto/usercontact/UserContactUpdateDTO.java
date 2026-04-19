package com.centros_sass.app.dto.usercontact;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserContactUpdateDTO {

    @Size(max = 25, message = "El nombre no puede exceder 25 caracteres")
    private String contactName;

    private Integer contactRelationshipId;

    @Size(max = 20, message = "El teléfono no puede exceder 20 caracteres")
    private String contactPhone;

    @Size(max = 255, message = "El email no puede exceder 255 caracteres")
    private String contactEmail;

    private Boolean isContactMain;

    private String contactNote;

    private Boolean isActive;
}