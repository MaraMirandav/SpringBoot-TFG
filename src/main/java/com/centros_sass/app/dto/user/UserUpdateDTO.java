package com.centros_sass.app.dto.user;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO de actualización para modificar un usuario existente.
 *
 * Utiliza clase con Lombok ya que todos los campos son opcionales.
 *
 * @author Equipo de Desarrollo
 * @version 1.0.0
 * @since 1.0.0
 */
@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateDTO {

    @Size(max = 25, message = "El primer nombre no puede exceder los 25 caracteres")
    private String firstName;

    @Size(max = 25, message = "El segundo nombre no puede exceder los 25 caracteres")
    private String secondName;

    @Size(max = 25, message = "El primer apellido no puede exceder los 25 caracteres")
    private String firstSurname;

    @Size(max = 25, message = "El segundo apellido no puede exceder los 25 caracteres")
    private String secondSurname;

    @Size(max = 25, message = "El alias no puede exceder los 25 caracteres")
    private String alias;

    @Email(message = "El email no es válido")
    @Size(max = 255, message = "El email no puede exceder los 255 caracteres")
    private String email;

    @Size(max = 20, message = "El teléfono no puede exceder los 20 caracteres")
    private String phone;

    @Size(max = 20, message = "El móvil no puede exceder los 20 caracteres")
    private String cellphone;

    @Size(max = 9, message = "El DNI/NIE no puede exceder los 9 caracteres")
    @Pattern(regexp = "^[0-9]{8}[A-Za-z]$|^[XYZxyz][0-9]{7}[A-Za-z]$",
             message = "El DNI/NIE es inválido. Formato esperado: 12345678A o X1234567A")
    private String dni;

    private Integer sexId;

    @Past(message = "La fecha de nacimiento debe ser una fecha pasada")
    private LocalDate birthDate;

    private Integer dependencyId;

    private Boolean isActive;

}
