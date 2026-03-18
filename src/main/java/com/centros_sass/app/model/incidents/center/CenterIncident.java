package com.centros_sass.app.model.incidents.center;

import com.centros_sass.app.model.incidents.Incident;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "cd_incidents")
@Getter
@Setter
public class CenterIncident extends Incident {

    @ManyToOne
    @JoinColumn(name = "incident_cd_id", nullable = false)
    private CdIncidentType cdIncident;

    @ManyToOne
    @JoinColumn(name = "significance_cd_id", nullable = false)
    private CdSignificanceType cdSignificance;

    @Override
    public String toString() {
        return "CenterIncident [" + super.toString() + ", cdIncident=" + cdIncident +
        ", cdSignificance=" + cdSignificance + "]";
    }
}
