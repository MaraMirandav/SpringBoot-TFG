package com.centros_sass.app.model.catalogs.dynamic.treatments;

import java.util.HashSet;
import java.util.Set;

import com.centros_sass.app.model.base.BaseEntity;
import com.centros_sass.app.model.treatments.Medication;

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
@Table(name = "medication_application_enum")
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(onlyExplicitlyIncluded = true, callSuper = false)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class MedicationApplication extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @ToString.Include
    private Integer id;

    @NonNull
    @Column(name = "medication_application_name", nullable = false, length = 100, columnDefinition = "VARCHAR", unique = true)
    private String medicationApplicationName;

    @NonNull
    @Column(name = "is_active", nullable = false, columnDefinition = "BOOLEAN DEFAULT true")
    private Boolean isActive = true;

    // RELATIONS
    // // Medications
    @OneToMany(mappedBy = "medicationApplication", fetch = FetchType.LAZY)
    private Set<Medication> medications = new HashSet<>();

    public void addMedication(Medication medication) {
        medications.add(medication);
        medication.setMedicationApplication(this);
    }

    public void removeMedication(Medication medication) {
        medications.remove(medication);
        medication.setMedicationApplication(null);
    }
}
