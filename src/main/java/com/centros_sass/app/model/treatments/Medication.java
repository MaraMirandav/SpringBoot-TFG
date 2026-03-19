package com.centros_sass.app.model.treatments;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import com.centros_sass.app.model.base.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "medications")
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class Medication extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @ToString.Include
    private Integer id;

    @NotNull(message = "{medication.medicationName.required}")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medication_name_id", nullable = false)
    private MedicationName medicationName;

    @NotBlank(message = "{medication.dose.required}")
    @Column(name = "dose", nullable = false, columnDefinition = "TEXT")
    private String dose;

    @NotNull(message = "{medication.receptionDate.required}")
    @Column(name = "reception_date", nullable = false, columnDefinition = "DATE")
    private LocalDate receptionDate;

    @NotNull(message = "{medication.expirationDate.required}")
    @Column(name = "expiration_date", nullable = false, columnDefinition = "DATE")
    private LocalDate expirationDate;

    @NotNull(message = "{medication.storageCondition.required}")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "storage_condition_id", nullable = false)
    private StorageCondition storageCondition;

    @NotNull(message = "{medication.medicationApplication.required}")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medication_application_id", nullable = false)
    private MedicationApplication medicationApplication;

    // RELATIONS
    // // TreatmentDetails
    @ManyToMany(mappedBy = "medications", fetch = FetchType.LAZY)
    private Set<TreatmentDetail> treatmentDetails = new HashSet<>();

    // // UserAllergies
    @ManyToMany(mappedBy = "medications", fetch = FetchType.LAZY)
    private Set<UserAllergy> userAllergies = new HashSet<>();
}