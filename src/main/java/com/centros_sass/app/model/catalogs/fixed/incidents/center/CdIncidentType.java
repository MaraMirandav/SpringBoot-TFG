package com.centros_sass.app.model.catalogs.fixed.incidents.center;

import java.util.HashSet;
import java.util.Set;

import com.centros_sass.app.model.base.BaseEntity;
import com.centros_sass.app.model.incidents.center.CenterIncident;

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
@Table(name = "incident_cd_enum")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(onlyExplicitlyIncluded = true, callSuper = false)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class CdIncidentType extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ToString.Include
    @EqualsAndHashCode.Include
    private Integer id;

    @NonNull
    @Column(name = "incident_name", nullable = false, columnDefinition = "TEXT", unique = true)
    private String incidentName;

    // RELATIONS
    // // CenterIncident
    @OneToMany(mappedBy = "cdIncident", fetch = FetchType.LAZY)
    private Set<CenterIncident> centerIncidents = new HashSet<>();

    public void addCenterIncident(CenterIncident centerIncident) {
        centerIncidents.add(centerIncident);
        centerIncident.setCdIncident(this);
    }
    public void removeCenterIncident(CenterIncident centerIncident) {
        centerIncidents.remove(centerIncident);
        centerIncident.setCdIncident(null);
    }
}
