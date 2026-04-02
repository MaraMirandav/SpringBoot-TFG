package com.centros_sass.app.model.incidents.user;

import java.util.HashSet;
import java.util.Set;

import com.centros_sass.app.model.catalogs.fixed.incidents.user.UserIncidentType;
import com.centros_sass.app.model.catalogs.fixed.incidents.user.UserSignificanceType;
import com.centros_sass.app.model.incidents.Incident;
import com.centros_sass.app.model.profiles.users.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "users_incidents")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(onlyExplicitlyIncluded = true, callSuper = false)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class UserIncident extends Incident {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ToString.Include
    @EqualsAndHashCode.Include
    private Integer id;

    @NonNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @NonNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "incident_user_id", nullable = false)
    private UserIncidentType userIncident;

    @NonNull
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

    @NonNull
    @Column(name = "is_active", nullable = false, columnDefinition = "BOOLEAN DEFAULT true")
    private Boolean isActive = true;
}
