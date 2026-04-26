package com.centros_sass.app.model.catalogs.calendar;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

import com.centros_sass.app.model.base.BaseEntity;
import com.centros_sass.app.model.profiles.users.UserAttendanceDay;
import com.centros_sass.app.model.profiles.workers.WorkerSchedule;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.NonNull;
import lombok.ToString;

@Entity
@Table(name = "open_days")
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(onlyExplicitlyIncluded = true, callSuper = false)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class OpenDay extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @ToString.Include
    private Integer id;

    @NonNull
    @Column(name = "day_name", nullable = false, length = 20, columnDefinition = "VARCHAR", unique = true)
    private String dayName;

    @NonNull
    @Column(name = "open_at", nullable = false, columnDefinition = "TIME")
    private LocalTime openAt;

    @NonNull
    @Column(name = "close_at", nullable = false, columnDefinition = "TIME")
    private LocalTime closeAt;

    // RELATIONS
    // // WorkerSchedule
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

    // // UserAttendanceDay
    @OneToMany(mappedBy = "day", fetch = FetchType.LAZY)
    private Set<UserAttendanceDay> attendanceDays = new HashSet<>();

    public void addAttendanceDay(UserAttendanceDay attendanceDay) {
        this.attendanceDays.add(attendanceDay);
        attendanceDay.setDay(this);
    }
    public void removeAttendanceDay(UserAttendanceDay attendanceDay) {
        this.attendanceDays.remove(attendanceDay);
        attendanceDay.setDay(null);
    }
}
