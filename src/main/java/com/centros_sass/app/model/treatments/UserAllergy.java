package com.centros_sass.app.model.treatments;

import java.util.HashSet;
import java.util.Set;

import com.centros_sass.app.model.base.BaseEntity;
import com.centros_sass.app.model.catalogs.dynamic.treatments.Allergy;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "user_allergies")
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class UserAllergy extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @ToString.Include
    private Integer id;

    @NonNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_medical_info_id", nullable = false)
    private UserMedicalInfo userMedicalInfo;

    @NonNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "allergy_id", nullable = false)
    private Allergy allergy;

    @Column(name = "comment", columnDefinition = "TEXT")
    private String comment;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "allergies_medications",
        joinColumns = @JoinColumn(name = "user_allergy_id"),
        inverseJoinColumns = @JoinColumn(name = "medication_id")
    )
    private Set<Medication> medications = new HashSet<>();

    public void addMedication(Medication medication) {
        medications.add(medication);
        medication.getUserAllergies().add(this);
    }

    public void removeMedication(Medication medication) {
        medications.remove(medication);
        medication.getUserAllergies().remove(this);
    }

    @NonNull
    @Column(name = "is_active", nullable = false, columnDefinition = "BOOLEAN DEFAULT true")
    private Boolean isActive = true;
}
