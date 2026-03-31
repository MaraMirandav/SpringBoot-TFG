package com.centros_sass.app.model.incidents.user;

import java.util.HashSet;
import java.util.Set;

import com.centros_sass.app.model.incidents.Incident;
import com.centros_sass.app.model.profiles.users.User;

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
@Table(name = "users_incidents")
@Getter @Setter
@NoArgsConstructor
public class UserIncident extends Incident {

    @NotNull(message = "{userIncident.user.required}")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @NotNull(message = "{userIncident.userIncident.required}")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "incident_user_id", nullable = false)
    private UserIncidentType userIncident;

    @NotNull(message = "{userIncident.userSignificance.required}")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "significance_user_id", nullable = false)
    private UserSignificanceType userSignificance;

    // RELATIONS
    // // UserIncidentComment
    @OneToMany(mappedBy = "userIncident", fetch = FetchType.LAZY)
    private Set<UserIncidentComment> userIncidentComments = new HashSet<>();

    public void addUserIncidentComment(UserIncidentComment userIncidentComment) {
        userIncidentComments.add(userIncidentComment);
        userIncidentComment.setUserIncident(this);
    }
    public void removeUserIncidentComment(UserIncidentComment userIncidentComment) {
        userIncidentComments.remove(userIncidentComment);
        userIncidentComment.setUserIncident(null);
    }
}
