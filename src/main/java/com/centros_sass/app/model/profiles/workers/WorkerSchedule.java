package com.centros_sass.app.model.profiles.workers;

import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

import com.centros_sass.app.model.base.BaseEntity;
import com.centros_sass.app.model.catalogs.calendar.OpenDay;

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
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "workers_schedules")
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(onlyExplicitlyIncluded = true, callSuper = false)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class WorkerSchedule extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @ToString.Include
    private Integer id;

    @NonNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "worker_id", nullable = false)
    private Worker worker;

    @NonNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "day_id", nullable = false)
    private OpenDay openDay;

    @OneToMany(mappedBy = "schedule", fetch = FetchType.LAZY)
    private Set<WorkerScheduleRecord> records = new HashSet<>();

    public void addRecord(WorkerScheduleRecord record) {
        this.records.add(record);
        record.setSchedule(this);
    }

    public void removeRecord(WorkerScheduleRecord record) {
        this.records.remove(record);
        record.setSchedule(null);
    }

    @NonNull
    @Column(name = "start_at", nullable = false, columnDefinition = "TIME")
    private LocalTime startAt;

    @NonNull
    @Column(name = "end_at", nullable = false, columnDefinition = "TIME")
    private LocalTime endAt;

    @NonNull
    @Column(name = "is_active", nullable = false, columnDefinition = "BOOLEAN DEFAULT true")
    private Boolean isActive = true;
}
