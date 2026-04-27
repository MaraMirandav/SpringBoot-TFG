package com.centros_sass.app.model.catalogs.incidents;

import java.util.HashSet;
import java.util.Set;

import com.centros_sass.app.model.base.BaseEntity;
import com.centros_sass.app.model.incidents.center.CenterIncident;
import com.centros_sass.app.model.incidents.user.UserIncident;

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
import lombok.Setter;
import lombok.NonNull;
import lombok.ToString;

@Entity
@Table(name = "incident_status_enum")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(onlyExplicitlyIncluded = true, callSuper = false)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class IncidentStatus extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ToString.Include
    @EqualsAndHashCode.Include
    private Integer id;

    @NonNull
    @Column(name = "status_name", nullable = false, length = 50, columnDefinition = "VARCHAR", unique = true)
    private String statusName;

    // RELATIONS
    // // CenterIncident
    @OneToMany(mappedBy = "incidentStatus", fetch = FetchType.LAZY)
    private Set<CenterIncident> centerIncidents = new HashSet<>();

    public void addCenterIncident(CenterIncident incident) {
        this.centerIncidents.add(incident);
        incident.setIncidentStatus(this);
    }

    public void removeCenterIncident(CenterIncident incident) {
        this.centerIncidents.remove(incident);
        incident.setIncidentStatus(null);
    }

    // // UserIncident
    @OneToMany(mappedBy = "incidentStatus", fetch = FetchType.LAZY)
    private Set<UserIncident> userIncidents = new HashSet<>();

    public void addUserIncident(UserIncident incident) {
        this.userIncidents.add(incident);
        incident.setIncidentStatus(this);
    }

    public void removeUserIncident(UserIncident incident) {
        this.userIncidents.remove(incident);
        incident.setIncidentStatus(null);
    }
}
