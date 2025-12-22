package com.centros_sass.app.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "workers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Worker implements Serializable {

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

    @Column(name = "main_phone", nullable = false, columnDefinition = "TEXT")
    private String mainPhone;

    @Column(name = "second_phone", columnDefinition = "TEXT")
    private String secondPhone;

    @Column(name = "email", nullable = false, columnDefinition = "TEXT")
    private String email;

    @Column(name = "password", nullable = false, columnDefinition = "TEXT")
    private String password;

    @Column(name = "is_active", nullable = false)
    private boolean isActive;
}
