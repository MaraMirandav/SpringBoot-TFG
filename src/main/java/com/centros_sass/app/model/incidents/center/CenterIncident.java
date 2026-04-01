package com.centros_sass.app.model.incidents.center;

import java.util.HashSet;
import java.util.Set;

import com.centros_sass.app.model.catalogs.fixed.incidents.center.CdIncidentType;
import com.centros_sass.app.model.catalogs.fixed.incidents.center.CdSignificanceType;
import com.centros_sass.app.model.incidents.Incident;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Entity
@Table(name = "cd_incidents")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class CenterIncident extends Incident {

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
}
