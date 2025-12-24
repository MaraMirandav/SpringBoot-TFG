package com.centros_sass.app.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "workers_schedules_records")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkerScheduleRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "worker_id", nullable = false, columnDefinition = "INTEGER" )
    private Worker worker;

    @OneToOne
    @JoinColumn(name = "schedule_id", nullable = false, columnDefinition = "INTEGER" )
    private WorkerSchedule schedule;

    @Column(name = "clock_in", nullable = false, columnDefinition = "TIMESTAMP")
    private LocalDateTime clockIn;

    @Column(name = "clock_out", columnDefinition = "TIMESTAMP")
    private LocalDateTime clockOut;

    @Column(name = "is_active", nullable = false)
    private boolean isActive;

}
