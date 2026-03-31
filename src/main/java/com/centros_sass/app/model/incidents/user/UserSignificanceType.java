package com.centros_sass.app.model.incidents.user;

import java.util.HashSet;
import java.util.Set;

import com.centros_sass.app.model.base.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "significance_user_enum")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class UserSignificanceType extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ToString.Include
    @EqualsAndHashCode.Include
    private Integer id;

    @NotBlank(message = "{userSignificanceType.significanceName.required}")
    @Column(name = "significance_name", nullable = false, columnDefinition = "TEXT", unique = true)
    private String significanceName;

    // RELATIONS
    // // UserIncident
    @OneToMany(mappedBy = "userSignificance")
    private Set<UserIncident> userIncidents = new HashSet<>();

    public void addUserIncident(UserIncident userIncident) {
        userIncidents.add(userIncident);
        userIncident.setUserSignificance(this);
    }
    public void removeUserIncident(UserIncident userIncident) {
        userIncidents.remove(userIncident);
        userIncident.setUserSignificance(null);
    }
}
