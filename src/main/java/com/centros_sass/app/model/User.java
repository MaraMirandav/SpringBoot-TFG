package com.centros_sass.app.model;

import java.io.Serializable;
import java.time.LocalDate;

import com.centros_sass.app.model.catalogs.Dependency;
import com.centros_sass.app.model.catalogs.Sex;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "first_name", nullable = false, columnDefinition = "TEXT")
    private String firstName;

    @Column(name = "second_name", columnDefinition = "TEXT")
    private String secondName;

    @Column(name = "first_surname", nullable = false, columnDefinition = "TEXT")
    private String firstSurname;

    @Column(name = "second_surname", columnDefinition = "TEXT")
    private String secondSurname;

    @Column(name = "alias", columnDefinition = "TEXT")
    private String alias;

    @Column(name = "dni", nullable = false, columnDefinition = "TEXT")
    private String dni;

    @ManyToOne
    @JoinColumn(name = "sex_id", nullable = false)
    private Sex sex;

    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @ManyToOne
    @JoinColumn(name = "dependency_id", nullable = false)
    private Dependency dependency;

    @Column(name = "is_active", nullable = false)
    private boolean isActive;
}
