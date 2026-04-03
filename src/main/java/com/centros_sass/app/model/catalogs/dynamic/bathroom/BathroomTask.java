package com.centros_sass.app.model.catalogs.dynamic.bathroom;

import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

import com.centros_sass.app.model.base.BaseEntity;
import com.centros_sass.app.model.bathroom.BathroomSchedule;

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
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "bathroom_tasks_enum")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(onlyExplicitlyIncluded = true, callSuper = false)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class BathroomTask extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ToString.Include
    @EqualsAndHashCode.Include
    private Integer id;

    @NonNull
    @Column(name = "task_name", nullable = false, length = 50, columnDefinition = "VARCHAR", unique = true)
    private String taskName;

    @NonNull
    @Column(name = "estimated_time", nullable = false, columnDefinition = "TIME")
    private LocalTime estimatedTime;

    @NonNull
    @Column(name = "is_active", nullable = false, columnDefinition = "BOOLEAN DEFAULT true")
    private Boolean isActive = true;

    // RELATIONS
    // // BathroomSchedule
    @OneToMany(mappedBy = "bathroomTask", fetch = FetchType.LAZY)
    private Set<BathroomSchedule> bathroomSchedules = new HashSet<>();

    public void addBathroomSchedule(BathroomSchedule schedule) {
        this.bathroomSchedules.add(schedule);
        schedule.setBathroomTask(this);
    }

    public void removeBathroomSchedule(BathroomSchedule schedule) {
        this.bathroomSchedules.remove(schedule);
        schedule.setBathroomTask(null);
    }
}
