package com.centros_sass.app.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.centros_sass.app.model.catalogs.OpenDay;

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
@Table(name = "workers_schedules")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkerSchedule implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "worker_id", nullable = false, columnDefinition = "INTEGER" )
    private Worker worker;

    @ManyToOne
    @JoinColumn(name = "day_id", nullable = false, columnDefinition = "INTEGER" )
    private OpenDay openDay;

    @Column(name = "start_at", nullable = false, columnDefinition = "TIMESTAMP")
    private LocalDateTime startAt;

    @Column(name = "end_at", nullable = false, columnDefinition = "TIMESTAMP")
    private LocalDateTime endAt;

}
