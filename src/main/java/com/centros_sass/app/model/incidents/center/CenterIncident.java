package com.centros_sass.app.model.incidents.center;

import java.util.HashSet;
import java.util.Set;

import com.centros_sass.app.model.incidents.Incident;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "cd_incidents")
@Getter @Setter
@NoArgsConstructor
public class CenterIncident extends Incident {

    @NotNull(message = "{centerIncident.cdIncident.required}")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "incident_cd_id", nullable = false)
    private CdIncidentType cdIncident;

    @NotNull(message = "{centerIncident.cdSignificance.required}")
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
