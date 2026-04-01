package com.centros_sass.app.model.profiles.workers;

import java.util.HashSet;
import java.util.Set;

import com.centros_sass.app.model.base.BaseEntity;
import com.centros_sass.app.model.catalogs.fixed.organization.Position;
import com.centros_sass.app.model.catalogs.fixed.organization.Role;
import com.centros_sass.app.model.incidents.Comment;
import com.centros_sass.app.model.incidents.Incident;
import com.centros_sass.app.model.treatments.UserMedicalInfo;

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
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
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

    @NonNull
    @Column(name = "first_name", nullable = false, columnDefinition = "TEXT")
    private String firstName;

    @Column(name = "second_name", columnDefinition = "TEXT")
    private String secondName;

    @NonNull
    @Column(name = "first_surname", nullable = false, columnDefinition = "TEXT")
    private String firstSurname;

    @Column(name = "second_surname", columnDefinition = "TEXT")
    private String secondSurname;

    @NonNull
    @Column(name = "dni_nie", nullable = false, unique = true, columnDefinition = "TEXT")
    private String dni;

    @Column(name = "main_phone", nullable = false, columnDefinition = "TEXT")
    private String mainPhone;

    @Column(name = "second_phone", columnDefinition = "TEXT")
    private String secondPhone;

    @NonNull
    @Column(name = "email", nullable = false, unique = true, columnDefinition = "TEXT")
    private String email;

    @NonNull
    @Column(name = "password", nullable = false, columnDefinition = "TEXT")
    private String password;

    @NonNull
    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    // RELATIONS
    // // Schedules
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

    // // Roles
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

    // // Positions
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

    // Incident
    @OneToMany(mappedBy = "createdBy", fetch = FetchType.LAZY)
    private Set<Incident> incidents = new HashSet<>();

    public void addIncident(Incident incident) {
        incidents.add(incident);
        incident.setCreatedBy(this);
    }
    public void removeIncident(Incident incident) {
        incidents.remove(incident);
        incident.setCreatedBy(null);
    }

    // Comments
    @OneToMany(mappedBy = "worker", fetch = FetchType.LAZY)
    private Set<Comment> comments = new HashSet<>();

    public void addComment(Comment comment) {
        comments.add(comment);
        comment.setWorker(this);
    }
    public void removeComment(Comment comment) {
        comments.remove(comment);
        comment.setWorker(null);
    }

    // // UserMedicalInfo
    @OneToMany(mappedBy = "worker", fetch = FetchType.LAZY)
    private Set<UserMedicalInfo> userMedicalInfos = new HashSet<>();

    public void addUserMedicalInfo(UserMedicalInfo userMedicalInfo) {
        userMedicalInfos.add(userMedicalInfo);
        userMedicalInfo.setWorker(this);
    }

    public void removeUserMedicalInfo(UserMedicalInfo userMedicalInfo) {
        userMedicalInfos.remove(userMedicalInfo);
        userMedicalInfo.setWorker(null);
    }
}
