package com.centros_sass.app.model.catalogs.dynamic.treatments;

import java.util.HashSet;
import java.util.Set;

import com.centros_sass.app.model.base.BaseEntity;
import com.centros_sass.app.model.treatments.UserIllness;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.NonNull;
import lombok.ToString;

@Entity
@Table(name = "illnesses_enum")
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(onlyExplicitlyIncluded = true, callSuper = false)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class Illness extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @ToString.Include
    private Integer id;

    @NonNull
    @Column(name = "illness_name", nullable = false, length = 100, columnDefinition = "VARCHAR", unique = true)
    private String illnessName;

    // RELATIONS
    // // UserIllness
    @OneToMany(mappedBy = "illness", fetch = FetchType.LAZY)
    private Set<UserIllness> userIllnesses = new HashSet<>();

    public void addUserIllness(UserIllness userIllness) {
        userIllnesses.add(userIllness);
        userIllness.setIllness(this);
    }

    public void removeUserIllness(UserIllness userIllness) {
        userIllnesses.remove(userIllness);
        userIllness.setIllness(null);
    }
}
