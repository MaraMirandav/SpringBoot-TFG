package com.centros_sass.app.model.profiles.users;

import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

import com.centros_sass.app.model.base.BaseEntity;
import com.centros_sass.app.model.catalogs.fixed.calendar.OpenDay;

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
@Table(name = "user_attendance_days")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(onlyExplicitlyIncluded = true, callSuper = false)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class UserAttendanceDay extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ToString.Include
    @EqualsAndHashCode.Include
    private Integer id;

    @NonNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @NonNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "day_id", nullable = false)
    private OpenDay day;

    @NonNull
    @Column(name = "start_at", nullable = false, columnDefinition = "TIME")
    private LocalTime startAt;

    @NonNull
    @Column(name = "end_at", nullable = false, columnDefinition = "TIME")
    private LocalTime endAt;

    @NonNull
    @Column(name = "is_active", nullable = false, columnDefinition = "BOOLEAN DEFAULT true")
    private Boolean isActive = true;

    // RELATIONS
    // // UserAttendanceRecord
    @OneToMany(mappedBy = "attendanceDay", fetch = FetchType.LAZY)
    private Set<UserAttendanceRecord> attendanceRecords = new HashSet<>();

    public void addAttendanceRecord(UserAttendanceRecord record) {
        this.attendanceRecords.add(record);
        record.setAttendanceDay(this);
    }

    public void removeAttendanceRecord(UserAttendanceRecord record) {
        this.attendanceRecords.remove(record);
        record.setAttendanceDay(null);
    }
}
