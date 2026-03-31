package com.centros_sass.app.model.incidents;

import java.util.HashSet;
import java.util.Set;

import com.centros_sass.app.model.base.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "incident_status_enum")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class IncidentStatus extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ToString.Include
    @EqualsAndHashCode.Include
    private Integer id;

    @NotBlank(message = "{incidentStatus.statusName.required}")
    @Column(name = "status_name", nullable = false, columnDefinition = "TEXT", unique = true)
    private String statusName;

    // RELATIONS
    // // Incident
    @OneToMany(mappedBy = "incidentStatus", fetch = FetchType.LAZY)
    private Set<Incident> incidents = new HashSet<>();

    public void addIncident(Incident incident) {
        incidents.add(incident);
        incident.setIncidentStatus(this);
    }
    public void removeIncident(Incident incident) {
        incidents.remove(incident);
        incident.setIncidentStatus(null);
    }

}
