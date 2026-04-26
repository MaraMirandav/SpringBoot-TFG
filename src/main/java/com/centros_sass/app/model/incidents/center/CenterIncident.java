package com.centros_sass.app.model.incidents.center;

import java.util.HashSet;
import java.util.Set;

import com.centros_sass.app.model.catalogs.incidents.center.CdIncidentType;
import com.centros_sass.app.model.catalogs.incidents.center.CdSignificanceType;
import com.centros_sass.app.model.incidents.Incident;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "cd_incidents")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(onlyExplicitlyIncluded = true, callSuper = false)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class CenterIncident extends Incident {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ToString.Include
    @EqualsAndHashCode.Include
    private Integer id;

    @NonNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "incident_cd_id", nullable = false)
    private CdIncidentType cdIncident;

    @NonNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "significance_cd_id", nullable = false)
    private CdSignificanceType cdSignificance;

    // RELATIONS
    // // CenterIncidentComment
    @OneToMany(mappedBy = "cdIncident", fetch = FetchType.LAZY)
    private Set<CenterIncidentComment> centerIncidentComments = new HashSet<>();

    public void addCenterIncidentComment(CenterIncidentComment centerIncidentComment) {
        centerIncidentComments.add(centerIncidentComment);
        centerIncidentComment.setCdIncident(this);
    }
    public void removeCenterIncidentComment(CenterIncidentComment centerIncidentComment) {
        centerIncidentComments.remove(centerIncidentComment);
        centerIncidentComment.setCdIncident(null);
    }

    @NonNull
    @Column(name = "is_active", nullable = false, columnDefinition = "BOOLEAN DEFAULT true")
    private Boolean isActive = true;
}
