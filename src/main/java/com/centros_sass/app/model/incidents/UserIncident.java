package com.centros_sass.app.model.incidents;

import com.centros_sass.app.model.User;
import com.centros_sass.app.model.catalogs.incidents.UserIncidentType;
import com.centros_sass.app.model.catalogs.incidents.UserSignificanceType;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "users_incidents")
@Getter
@Setter
public class UserIncident extends Incident {

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "incident_user_id", nullable = false)
    private UserIncidentType userIncident;

    @ManyToOne
    @JoinColumn(name = "significance_user_id", nullable = false)
    private UserSignificanceType userSignificance;

    @Override
    public String toString() {
        return "UserIncident [" + super.toString() + ", user=" + user +
        ", userIncident=" + userIncident + ", userSignificance=" + userSignificance + "]";
    }
}
