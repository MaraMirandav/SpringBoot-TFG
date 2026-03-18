package com.centros_sass.app.model.incidents.user;

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
@Table(name = "incidents_users_comments")
@Getter @Setter
@NoArgsConstructor
public class UserIncidentComment extends Comment {

    @NotNull(message = "{userIncidentComment.userIncident.required}")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_incident_id", nullable = false)
    private UserIncident userIncident;
}
