package com.centros_sass.app.model.incidents.user;

import com.centros_sass.app.model.incidents.Comment;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "incidents_users_comments")
@Getter
@Setter
public class UserIncidentComment extends Comment {

    @ManyToOne
    @JoinColumn(name = "user_incident_id", nullable = false)
    private UserIncident userIncident;

    @Override
    public String toString() {
        return "UserIncidentComment [" + super.toString() + ", UserIncident=" + userIncident + "]";
    }
}
