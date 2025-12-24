package com.centros_sass.app.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.centros_sass.app.model.base.BaseEntity;
import com.centros_sass.app.model.catalogs.OpenDay;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "workers_schedules")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WorkerSchedule extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "worker_id", nullable = false, columnDefinition = "INTEGER" )
    private Worker worker;

    @ManyToOne
    @JoinColumn(name = "day_id", nullable = false, columnDefinition = "INTEGER" )
    private OpenDay openDay;

    @Column(name = "start_at", nullable = false, columnDefinition = "TIMESTAMP")
    private LocalDateTime startAt;

    @Column(name = "end_at", nullable = false, columnDefinition = "TIMESTAMP")
    private LocalDateTime endAt;

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
        WorkerSchedule other = (WorkerSchedule) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "WorkerSchedule [id=" + id + ", worker=" + worker + ", openDay=" + openDay + ", startAt=" + startAt
                + ", endAt=" + endAt + "]";
    }
}
