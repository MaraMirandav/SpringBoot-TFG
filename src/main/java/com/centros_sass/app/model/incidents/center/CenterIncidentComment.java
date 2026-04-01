package com.centros_sass.app.model.incidents.center;

import com.centros_sass.app.model.incidents.Comment;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Entity
@Table(name = "incidents_cd_comments")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class CenterIncidentComment extends Comment {

    @NonNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cd_incident_id", nullable = false)
    private CenterIncident cdIncident;
}
