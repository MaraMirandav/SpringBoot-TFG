package com.centros_sass.app.model.transport;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

import com.centros_sass.app.model.base.BaseEntity;
import com.centros_sass.app.model.catalogs.fixed.transport.RouteShift;
import com.centros_sass.app.model.profiles.users.User;
import com.centros_sass.app.model.profiles.workers.Worker;

import jakarta.persistence.FetchType;
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
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "transport_routes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(onlyExplicitlyIncluded = true, callSuper = false)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class TransportRoute extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ToString.Include
    @EqualsAndHashCode.Include
    private Integer id;

    @Column(name = "route_number", nullable = false, columnDefinition = "INTEGER")
    private Integer routeNumber;

    @Column(name = "start_time", nullable = false, columnDefinition = "TIME")
    private LocalTime startTime;

    @Column(name = "end_time", nullable = false, columnDefinition = "TIME")
    private LocalTime endTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "route_shift_id", nullable = false)
    private RouteShift routeShift;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "route_vehicle_id", nullable = false)
    private RouteVehicle routeVehicle;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "worker_driver_id", nullable = false)
    private Worker driver;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "worker_copilot_id", nullable = false)
    private Worker copilot;

    @Column(name = "is_active", nullable = false, columnDefinition = "BOOLEAN")
    private boolean isActive;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "transport_routes_user",
        joinColumns = @JoinColumn(name = "route_id"),
        inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> users = new HashSet<>();

}
