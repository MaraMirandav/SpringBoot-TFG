package com.centros_sass.app.model.transport;

import com.centros_sass.app.model.base.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "route_vehicles")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(onlyExplicitlyIncluded = true, callSuper = false)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class RouteVehicle extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ToString.Include
    @EqualsAndHashCode.Include
    private Integer id;

    @NonNull
    @Column(name = "license_plate", nullable = false, length = 10, columnDefinition = "VARCHAR", unique = true)
    private String licensePlate;

    @NonNull
    @Column(name = "capacity", nullable = false, columnDefinition = "INTEGER")
    private Integer capacity;

    @NonNull
    @Column(name = "has_wheelchair", nullable = false, columnDefinition = "BOOLEAN")
    private Boolean hasWheelchair;

    @Column(name = "wheelchair_capacity", columnDefinition = "INTEGER")
    private Integer wheelchairCapacity;

    @NonNull
    @Column(name = "is_active", nullable = false, columnDefinition = "BOOLEAN DEFAULT true")
    private Boolean isActive = true;

}
