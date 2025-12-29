package com.centros_sass.app.model.transport;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

import com.centros_sass.app.model.base.BaseEntity;
import com.centros_sass.app.model.profiles.users.User;
import com.centros_sass.app.model.profiles.workers.Worker;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "transport_routes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransportRoute extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "route_number", nullable = false, columnDefinition = "INTEGER")
    private Integer routeNumber;

    @Column(name = "start_time", nullable = false, columnDefinition = "TIME")
    private LocalTime startTime;

    @Column(name = "end_time", nullable = false, columnDefinition = "TIME")
    private LocalTime endTime;

    @ManyToOne
    @JoinColumn(name = "route_shift_id", nullable = false)
    private RouteShift routeShift;

    @ManyToOne
    @JoinColumn(name = "route_vehicle_id", nullable = false)
    private RouteVehicle routeVehicle;

    @ManyToOne
    @JoinColumn(name = "worker_driver_id", nullable = false)
    private Worker driver;

    @ManyToOne
    @JoinColumn(name = "worker_copilot_id", nullable = false)
    private Worker copilot;

    @Column(name = "is_active", nullable = false, columnDefinition = "BOOLEAN")
    private boolean isActive;

    @ManyToMany
    @JoinTable(
        name = "transport_routes_user",
        joinColumns = @JoinColumn(name = "route_id"),
        inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> users = new HashSet<>();

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
        TransportRoute other = (TransportRoute) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "TransportRoute [id=" + id + ", routeNumber=" + routeNumber + ", startTime=" + startTime + ", endTime="
                + endTime + ", routeShift=" + routeShift + ", routeVehicule=" + routeVehicle + ", driver=" + driver
                + ", copilot=" + copilot + ", isActive=" + isActive + ", users=" + users + "]";
    }
}
