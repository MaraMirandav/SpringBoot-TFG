package com.centros_sass.app.model.incidents.center;

import com.centros_sass.app.model.incidents.Comment;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "incidents_cd_comments")
@Getter
@Setter
public class CenterIncidentComment extends Comment {

    @ManyToOne
    @JoinColumn(name = "cd_incident_id", nullable = false)
    private CenterIncident cdIncident;

    @Override
    public String toString() {
        return "CenterIncidentComment [" + super.toString() + ", cdIncident=" + cdIncident + "]";
    }
}
