package com.centros_sass.app.model.bathroom;

import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

import com.centros_sass.app.model.base.BaseEntity;

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
@Table(name = "bathroom_turns")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(onlyExplicitlyIncluded = true, callSuper = false)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class BathroomTurn extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ToString.Include
    @EqualsAndHashCode.Include
    private Integer id;

    @NonNull
    @Column(name = "turn_name", nullable = false, length = 50, columnDefinition = "VARCHAR", unique = true)
    private String turnName;

    @NonNull
    @Column(name = "start_at", nullable = false, columnDefinition = "TIME")
    private LocalTime startAt;

    @NonNull
    @Column(name = "end_at", nullable = false, columnDefinition = "TIME")
    private LocalTime endAt;

    // RELATIONS
    // // BathroomSchedule
    @OneToMany(mappedBy = "bathroomTurn", fetch = FetchType.LAZY)
    private Set<BathroomSchedule> bathroomSchedules = new HashSet<>();

    public void addBathroomSchedule(BathroomSchedule schedule) {
        this.bathroomSchedules.add(schedule);
        schedule.setBathroomTurn(this);
    }

    public void removeBathroomSchedule(BathroomSchedule schedule) {
        this.bathroomSchedules.remove(schedule);
        schedule.setBathroomTurn(null);
    }
}
