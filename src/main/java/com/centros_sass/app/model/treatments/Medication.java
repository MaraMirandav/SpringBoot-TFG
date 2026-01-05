package com.centros_sass.app.model.treatments;

import java.io.Serializable;
import java.time.LocalDate;

import com.centros_sass.app.model.base.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "medications")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Medication extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "medication_name_id", nullable = false)
    private MedicationName medicationName;

    @Column(name = "dose", nullable = false, columnDefinition = "TEXT")
    private String dose;

    @Column(name = "reception_date", nullable = false, columnDefinition = "DATE")
    private LocalDate receptionDate;

    @Column(name = "expiration_date", nullable = false, columnDefinition = "DATE")
    private LocalDate expirationDate;

    @ManyToOne
    @JoinColumn(name = "storage_condition_id", nullable = false)
    private StorageCondition storageCondition;

    @ManyToOne
    @JoinColumn(name = "medication_application_id", nullable = false)
    private MedicationApplication medicationApplication;

    // hashCode / equals / toString
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Medication other = (Medication) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Medication [id=" + id + ", medicationName=" + medicationName + ", dose=" + dose + ", receptionDate="
                + receptionDate + ", expirationDate=" + expirationDate + ", storageCondition=" + storageCondition
                + ", medicationApplication=" + medicationApplication + "]";
    }
}