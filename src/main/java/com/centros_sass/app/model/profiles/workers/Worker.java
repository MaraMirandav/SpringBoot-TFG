package com.centros_sass.app.model.profiles.workers;

import java.util.HashSet;
import java.util.Set;

import com.centros_sass.app.model.base.BaseEntity;
import com.centros_sass.app.model.catalogs.organization.Position;
import com.centros_sass.app.model.catalogs.organization.Role;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "workers")
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class Worker extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @ToString.Include
    private Integer id;

    @NotBlank(message = "{worker.firstName.required}")
    @Column(name = "first_name", nullable = false, columnDefinition = "TEXT")
    private String firstName;

    @Column(name = "second_name", columnDefinition = "TEXT")
    private String secondName;

    @NotBlank(message = "{worker.firstSurname.required}")
    @Column(name = "first_surname", nullable = false, columnDefinition = "TEXT")
    private String firstSurname;

    @Column(name = "second_surname", columnDefinition = "TEXT")
    private String secondSurname;

    @NotBlank(message = "{worker.dni.required}")
    @Pattern(regexp = "^(?:[0-9]{8}|[XYZxyz][0-9]{7})[A-Za-z]$", message = "{worker.dni.invalid}")
    @Column(name = "dni_nie", nullable = false, unique = true, columnDefinition = "TEXT")
    private String dni;

    @NotBlank(message = "{worker.mainPhone.required}")
    @Column(name = "main_phone", nullable = false, columnDefinition = "TEXT")
    private String mainPhone;

    @Column(name = "second_phone", columnDefinition = "TEXT")
    private String secondPhone;

    @NotBlank(message = "{worker.email.required}")
    @Email(message = "{worker.email.invalid}")
    @Column(name = "email", nullable = false, unique = true, columnDefinition = "TEXT")
    private String email;

    @NotBlank(message = "{worker.password.required}")
    @Pattern(regexp = "([A-Za-z]{8,})", message = "{worker.password.invalid}")
    @Column(name = "password", nullable = false, columnDefinition = "TEXT")
    private String password;

    @NotNull(message = "{worker.isActive.required}")
    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    // Schedules
    @OneToMany(mappedBy = "worker", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<WorkerSchedule> schedules = new HashSet<>();

    public void addSchedule(WorkerSchedule schedule) {
        this.schedules.add(schedule);
        schedule.setWorker(this);
    }

    public void removeSchedule(WorkerSchedule schedule) {
        this.schedules.remove(schedule);
        schedule.setWorker(null);
    }

    // Roles
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "workers_roles",
        joinColumns = @JoinColumn(name = "worker_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

    public void addRole(Role role) {
        this.roles.add(role);
    }

    public void removeRole(Role role) {
        this.roles.remove(role);
    }

    // Positions
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "workers_positions",
        joinColumns = @JoinColumn(name = "worker_id"),
        inverseJoinColumns = @JoinColumn(name = "position_id")
    )
    private Set<Position> positions = new HashSet<>();

    public void addPosition(Position position) {
        this.positions.add(position);
    }

    public void removePosition(Position position) {
        this.positions.remove(position);
    }
}
