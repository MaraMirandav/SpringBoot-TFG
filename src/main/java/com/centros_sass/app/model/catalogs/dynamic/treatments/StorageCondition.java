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
@Table(name = "storage_condition_enum")
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class StorageCondition extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @ToString.Include
    private Integer id;

    @NonNull
    @Column(name = "storage_name", nullable = false, columnDefinition = "TEXT", unique = true)
    private String storageName;

    // RELATIONS
    // // Medications
    @OneToMany(mappedBy = "storageCondition", fetch = FetchType.LAZY)
    private Set<Medication> medications = new HashSet<>();

    public void addMedication(Medication medication) {
        medications.add(medication);
        medication.setStorageCondition(this);
    }

    public void removeMedication(Medication medication) {
        medications.remove(medication);
        medication.setStorageCondition(null);
    }
}
