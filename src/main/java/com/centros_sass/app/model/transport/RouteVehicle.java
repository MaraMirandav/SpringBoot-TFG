package com.centros_sass.app.model.transport;

import java.io.Serializable;

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
@Table(name = "route_vehicles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RouteVehicle extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "license_plate", nullable = false, columnDefinition = "TEXT", unique = true)
    private String licensePlate;

    @Column(name = "capacity", nullable = false, columnDefinition = "INTEGER")
    private Integer capacity;

    @Column(name = "has_wheelchair", nullable = false, columnDefinition = "BOOLEAN")
    private boolean hasWheelchair;

    @Column(name = "wheelchair_capacity", columnDefinition = "INTEGER")
    private Integer wheelchairCapacity;

    @Column(name = "is_active", nullable = false, columnDefinition = "BOOLEAN")
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
        RouteVehicle other = (RouteVehicle) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "RouteVehicule [id=" + id + ", licensePlate=" + licensePlate + ", capacity=" + capacity
                + ", hasWheelchair=" + hasWheelchair + ", wheelchairCapacity=" + wheelchairCapacity + ", isActive="
                + isActive + "]";
    }
}
