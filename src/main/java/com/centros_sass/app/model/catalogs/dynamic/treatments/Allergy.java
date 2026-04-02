package com.centros_sass.app.model.catalogs.dynamic.treatments;

import java.util.HashSet;
import java.util.Set;

import com.centros_sass.app.model.base.BaseEntity;
import com.centros_sass.app.model.treatments.UserAllergy;

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
@Table(name = "allergies_enum")
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(onlyExplicitlyIncluded = true, callSuper = false)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class Allergy extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @ToString.Include
    private Integer id;

    @NonNull
    @Column(name = "allergy_name", nullable = false, columnDefinition = "TEXT", unique = true)
    private String allergyName;

    // RELATIONS
    // // UserAllergy
    @OneToMany(mappedBy = "allergy", fetch = FetchType.LAZY)
    private Set<UserAllergy> userAllergies = new HashSet<>();

    public void addUserAllergy(UserAllergy userAllergy) {
        userAllergies.add(userAllergy);
        userAllergy.setAllergy(this);
    }

    public void removeUserAllergy(UserAllergy userAllergy) {
        userAllergies.remove(userAllergy);
        userAllergy.setAllergy(null);
    }
}