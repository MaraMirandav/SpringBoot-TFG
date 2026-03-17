package com.centros_sass.app.model.profiles.users;

import java.time.LocalTime;

import com.centros_sass.app.model.base.BaseEntity;
import com.centros_sass.app.model.catalogs.calendar.OpenDay;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "user_attendance_days")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class UserAttendanceDay extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ToString.Include
    @EqualsAndHashCode.Include
    private Integer id;

    @NotNull(message = "{userAttendanceDay.user.required}")
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @NotNull(message = "{userAttendanceDay.day.required}")
    @ManyToOne
    @JoinColumn(name = "day_id", nullable = false)
    private OpenDay day;

    @NotNull(message = "{userAttendanceDay.startAt.required}")
    @Column(name = "start_at", nullable = false, columnDefinition = "TIME")
    private LocalTime startAt;

    @NotNull(message = "{userAttendanceDay.endAt.required}")
    @Column(name = "end_at", nullable = false, columnDefinition = "TIME")
    private LocalTime endAt;
}
