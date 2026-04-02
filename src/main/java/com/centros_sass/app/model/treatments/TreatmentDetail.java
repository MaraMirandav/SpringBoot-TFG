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
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "treatment_details")
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(onlyExplicitlyIncluded = true, callSuper = false)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class TreatmentDetail extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @ToString.Include
    private Integer id;

    @NonNull
    @Column(name = "start_date", nullable = false, columnDefinition = "DATE")
    private LocalDate startDate;

    @Column(name = "end_date", columnDefinition = "DATE")
    private LocalDate endDate;

    @NonNull
    @Column(name = "is_active", nullable = false, columnDefinition = "BOOLEAN")
    private Boolean isActive;

    @Column(name = "comment", columnDefinition = "TEXT")
    private String comment;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "treatment_details_medication",
        joinColumns = @JoinColumn(name = "treatment_detail_id"),
        inverseJoinColumns = @JoinColumn(name = "medication_id")
    )
    private Set<Medication> medications = new HashSet<>();

    public void addMedication(Medication medication) {
        this.medications.add(medication);
        medication.getTreatmentDetails().add(this);
    }

    public void removeMedication(Medication medication) {
        this.medications.remove(medication);
        medication.getTreatmentDetails().remove(this);
    }

    // RELATIONS
    // // UserIllnesses
    @ManyToMany(mappedBy = "treatmentDetails", fetch = FetchType.LAZY)
    private Set<UserIllness> userIllnesses = new HashSet<>();

    public void addUserIllness(UserIllness userIllness) {
        userIllnesses.add(userIllness);
        userIllness.getTreatmentDetails().add(this);
    }

    public void removeUserIllness(UserIllness userIllness) {
        userIllnesses.remove(userIllness);
        userIllness.getTreatmentDetails().remove(this);
    }
}

