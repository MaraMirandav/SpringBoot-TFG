package com.centros_sass.app.model.transport;

import java.time.LocalTime;

import com.centros_sass.app.model.base.BaseEntity;
import com.centros_sass.app.model.profiles.users.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(
    name = "transport_routes_user",
    uniqueConstraints = @UniqueConstraint(columnNames = {"route_id", "user_id"})
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(onlyExplicitlyIncluded = true, callSuper = false)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class TransportRouteUser extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ToString.Include
    @EqualsAndHashCode.Include
    private Integer id;

    @NonNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "route_id", nullable = false)
    private TransportRoute route;

    @NonNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "arrival_time", columnDefinition = "TIME")
    private LocalTime arrivalTime;

    @NonNull
    @Column(name = "uses_wheelchair", nullable = false, columnDefinition = "BOOLEAN DEFAULT false")
    private Boolean usesWheelchair = false;

    @Column(name = "comment", length = 500, columnDefinition = "VARCHAR")
    private String comment;
}
