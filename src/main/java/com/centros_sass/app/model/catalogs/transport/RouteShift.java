package com.centros_sass.app.model.catalogs.transport;

import java.util.HashSet;
import java.util.Set;

import com.centros_sass.app.model.base.BaseEntity;
import com.centros_sass.app.model.transport.TransportRoute;

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
@Table(name = "routes_shift_enum")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(onlyExplicitlyIncluded = true, callSuper = false)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class RouteShift extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ToString.Include
    @EqualsAndHashCode.Include
    private Integer id;

    @NonNull
    @Column(name = "route_name", nullable = false, length = 20, columnDefinition = "VARCHAR", unique = true)
    private String routeName;

    // RELATIONS
    // // TransportRoute
    @OneToMany(mappedBy = "routeShift", fetch = FetchType.LAZY)
    private Set<TransportRoute> transportRoutes = new HashSet<>();

    public void addTransportRoute(TransportRoute route) {
        this.transportRoutes.add(route);
        route.setRouteShift(this);
    }

    public void removeTransportRoute(TransportRoute route) {
        this.transportRoutes.remove(route);
        route.setRouteShift(null);
    }
}
