package com.centros_sass.app.model.profiles.users;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import com.centros_sass.app.model.base.BaseEntity;
import com.centros_sass.app.model.catalogs.people.Dependency;
import com.centros_sass.app.model.catalogs.people.Sex;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "users")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ToString.Include
    @EqualsAndHashCode.Include
    private Integer id;

    @NotBlank(message = "{user.firstName.required}")
    @Column(name = "first_name", nullable = false, columnDefinition = "TEXT")
    private String firstName;

    @Column(name = "second_name", columnDefinition = "TEXT")
    private String secondName;

    @NotBlank(message = "{user.firsSurename.required}")
    @Column(name = "first_surname", nullable = false, columnDefinition = "TEXT")
    private String firstSurname;

    @Column(name = "second_surname", columnDefinition = "TEXT")
    private String secondSurname;

    @Column(name = "alias", columnDefinition = "TEXT")
    private String alias;

    @Email(message = "{user.email.invalid}")
    @Column(name = "email", columnDefinition = "TEXT")
    private String email;

    @Column(name = "phone", columnDefinition = "TEXT")
    private String phone;

    @Column(name = "cellphone", columnDefinition = "TEXT")
    private String cellphone;

    @NotBlank(message = "{user.dni.required}")
    @Pattern(regexp = "^(?:[0-9]{8}|[XYZxyz][0-9]{7})[A-Za-z]$", message = "{user.dni.invalid}")
    @Column(name = "dni", nullable = false, columnDefinition = "TEXT")
    private String dni;

    @NotNull(message = "{user.sex.required}")
    @ManyToOne
    @JoinColumn(name = "sex_id", nullable = false)
    private Sex sex;

    @NotNull(message = "{user.birthDate.required}")
    @Past(message = "{user.birthDate.past}")
    @Column(name = "birth_date", nullable = false, columnDefinition = "DATE")
    private LocalDate birthDate;

    @NotNull(message = "{user.dependency.required}")
    @ManyToOne
    @JoinColumn(name = "dependency_id", nullable = false)
    private Dependency dependency;

    @NotNull(message = "{user.isActive.required}")
    @Column(name = "is_active", nullable = false, columnDefinition = "BOOLEAN")
    private Boolean isActive;

    // RELATIONS
    // // UserAdresses
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private Set<UserAddress> userAdresses = new HashSet<>();



}
