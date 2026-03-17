package com.centros_sass.app.model.catalogs.calendar;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

import com.centros_sass.app.model.base.BaseEntity;
import com.centros_sass.app.model.profiles.workers.WorkerSchedule;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "open_days")
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class OpenDay extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @ToString.Include
    private Integer id;

    @NotBlank(message = "{openDay.day.required}")
    @Column(name = "day", nullable = false, columnDefinition = "TEXT", unique = true)
    private String day;

    @NotNull(message = "{openDay.openAt.required}")
    @Column(name = "open_at", nullable = false, columnDefinition = "TIME")
    private LocalTime openAt;

    @NotNull(message = "{openDay.closeAt.required}")
    @Column(name = "close_at", nullable = false, columnDefinition = "TIME")
    private LocalTime closeAt;

    @OneToMany(mappedBy = "openDay", fetch = FetchType.LAZY)
    private Set<WorkerSchedule> schedules = new HashSet<>();

    public void addSchedule(WorkerSchedule schedule) {
        this.schedules.add(schedule);
        schedule.setOpenDay(this);
    }

    public void removeSchedule(WorkerSchedule schedule) {
        this.schedules.remove(schedule);
        schedule.setOpenDay(null);
    }
}
