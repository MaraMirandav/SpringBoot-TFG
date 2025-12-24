package com.centros_sass.app.model.catalogs;

import java.io.Serializable;
import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "open_days")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OpenDay implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "day", nullable = false, columnDefinition = "TEXT", unique = true)
    private String day;

    @Column(name = "open_at", nullable = false, columnDefinition = "TIME", unique = false)
    private LocalTime openAt;

    @Column(name = "close_at", nullable = false, columnDefinition = "TIME", unique = false)
    private LocalTime closeAt;

}
