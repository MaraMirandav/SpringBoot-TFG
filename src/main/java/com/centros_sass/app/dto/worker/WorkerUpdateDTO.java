package com.centros_sass.app.dto.worker;

import java.util.Set;

import jakarta.validation.constraints.Email;
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
public class WorkerUpdateDTO {

    @Size(max = 25, message = "El primer nombre no puede exceder los 25 caracteres")
    private String firstName;

    @Size(max = 25, message = "El segundo nombre no puede exceder los 25 caracteres")
    private String secondName;

    @Size(max = 25, message = "El primer apellido no puede exceder los 25 caracteres")
    private String firstSurname;

    @Size(max = 25, message = "El segundo apellido no puede exceder los 25 caracteres")
    private String secondSurname;

    @Size(max = 9, message = "El DNI no puede exceder los 9 caracteres")
    private String dni;

    @Size(max = 20, message = "El teléfono no puede exceder los 20 caracteres")
    private String mainPhone;

    @Size(max = 20, message = "El teléfono secundario no puede exceder los 20 caracteres")
    private String secondPhone;

    @Email(message = "El email no es válido")
    @Size(max = 255, message = "El email no puede exceder los 255 caracteres")
    private String email;

    @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
    private String password;

    private Set<Integer> roleIds;

    private Boolean isActive;

}
