package com.centros_sass.app.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.centros_sass.app.model.base.BaseEntity;

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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "workers_schedules_records")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WorkerScheduleRecord extends BaseEntity implements Serializable {

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

    // hashCode / equals / toString
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        WorkerScheduleRecord other = (WorkerScheduleRecord) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "WorkerScheduleRecord [id=" + id + ", worker=" + worker + ", schedule=" + schedule + ", clockIn="
                + clockIn + ", clockOut=" + clockOut + ", isActive=" + isActive + "]";
    }
}
