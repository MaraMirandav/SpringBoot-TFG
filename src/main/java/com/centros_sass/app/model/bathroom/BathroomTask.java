package com.centros_sass.app.model.bathroom;

import java.io.Serializable;
import java.time.LocalTime;

import com.centros_sass.app.model.base.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "bathroom_tasks")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BathroomTask extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "task_name", nullable = false, columnDefinition = "TEXT", unique = true)
    private String taskName;

    @Column(name = "estimated_time", nullable = false, columnDefinition = "TIME")
    private LocalTime estimatedTime;

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
        BathroomTask other = (BathroomTask) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "BathroomTask [id=" + id + ", taskName=" + taskName + ", estimatedTime=" + estimatedTime + "]";
    }
}
