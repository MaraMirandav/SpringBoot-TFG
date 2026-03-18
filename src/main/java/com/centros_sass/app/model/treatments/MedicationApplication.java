package com.centros_sass.app.model.treatments;

import java.util.HashSet;
import java.util.Set;

import com.centros_sass.app.model.base.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
@Table(name = "medication_application_enum")
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class MedicationApplication extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @ToString.Include
    private Integer id;

    @NotBlank(message = "{medicationApplication.medicationApplicationName.required}")
    @Column(name = "medication_application_name", nullable = false, columnDefinition = "TEXT", unique = true)
    private String medicationApplicationName;

    // Medications
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
