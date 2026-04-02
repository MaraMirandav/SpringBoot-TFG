package com.centros_sass.app.model.treatments;

import java.util.HashSet;
import java.util.Set;

import com.centros_sass.app.model.base.BaseEntity;
import com.centros_sass.app.model.profiles.users.User;
import com.centros_sass.app.model.profiles.workers.Worker;

import jakarta.persistence.CascadeType;
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
@Table(name = "user_medical_info")
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(onlyExplicitlyIncluded = true, callSuper = false)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class UserMedicalInfo extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @ToString.Include
    private Integer id;

    @NonNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @NonNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "worker_id", nullable = false)
    private Worker worker;

    @NonNull
    @Column(name = "is_active", nullable = false, columnDefinition = "BOOLEAN")
    private Boolean isActive;

    // RELATIONS
    // // UserAllergy
    @OneToMany(mappedBy = "userMedicalInfo", fetch = FetchType.LAZY,
        cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UserAllergy> userAllergies = new HashSet<>();

    public void addUserAllergy(UserAllergy userAllergy) {
        userAllergies.add(userAllergy);
        userAllergy.setUserMedicalInfo(this);
    }

    public void removeUserAllergy(UserAllergy userAllergy) {
        userAllergies.remove(userAllergy);
        userAllergy.setUserMedicalInfo(null);
    }

    // // UserIllness
    @OneToMany(mappedBy = "userMedicalInfo", fetch = FetchType.LAZY,
        cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UserIllness> userIllnesses = new HashSet<>();

    public void addUserIllness(UserIllness userIllness) {
        userIllnesses.add(userIllness);
        userIllness.setUserMedicalInfo(this);
    }

    public void removeUserIllness(UserIllness userIllness) {
        userIllnesses.remove(userIllness);
        userIllness.setUserMedicalInfo(null);
    }
}