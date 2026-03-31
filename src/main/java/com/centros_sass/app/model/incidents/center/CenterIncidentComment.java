package com.centros_sass.app.model.incidents.center;

import com.centros_sass.app.model.incidents.Comment;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "incidents_cd_comments")
@Getter @Setter
@NoArgsConstructor
public class CenterIncidentComment extends Comment {

    @NotNull(message = "{centerIncidentComment.cdIncident.required}")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cd_incident_id", nullable = false)
    private CenterIncident cdIncident;
}
