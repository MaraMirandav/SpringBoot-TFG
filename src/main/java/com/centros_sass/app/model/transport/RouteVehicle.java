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
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "route_vehicles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(onlyExplicitlyIncluded = true, callSuper = false)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class RouteVehicle extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ToString.Include
    @EqualsAndHashCode.Include
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

}
