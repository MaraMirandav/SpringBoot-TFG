package com.centros_sass.app.model.profiles.workers;

import java.time.LocalDateTime;

import com.centros_sass.app.model.base.BaseEntity;
import com.centros_sass.app.model.catalogs.calendar.OpenDay;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "workers_schedules")
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class WorkerSchedule extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @ToString.Include
    private Integer id;

    @NotNull(message = "{workerSchedule.workerId.required}")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "worker_id", nullable = false)
    private Worker worker;

    @NotNull(message = "{workerSchedule.dayId.required}")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "day_id", nullable = false)
    private OpenDay openDay;

    @OneToOne(mappedBy = "schedule", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private WorkerScheduleRecord record;

    @NotNull(message = "{workerSchedule.startAt.required}")
    @Column(name = "start_at", nullable = false, columnDefinition = "TIME")
    private LocalDateTime startAt;

    @NotNull(message = "{workerSchedule.endAt.required}")
    @Column(name = "end_at", nullable = false, columnDefinition = "TIME")
    private LocalDateTime endAt;
}
