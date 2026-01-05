package com.centros_sass.app.model.treatments;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.centros_sass.app.model.base.BaseEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user_illnesses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserIllness extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_medical_info_id", nullable = false)
    private UserMedicalInfo userMedicalInfo;

    @ManyToOne
    @JoinColumn(name = "illness_id", nullable = false)
    private Illness illness;

    @ManyToMany
    @JoinTable(
        name = "user_illness_treatment",
        joinColumns = @JoinColumn(name = "user_illness_id"),
        inverseJoinColumns = @JoinColumn(name = "treatment_detail_id")
    )
    private Set<TreatmentDetail> treatmentDetails = new HashSet<>();

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
        UserIllness other = (UserIllness) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "UserIllness [id=" + id + ", userMedicalInfo=" + userMedicalInfo + ", illness=" + illness
                + ", treatmentDetails=" + treatmentDetails + "]";
    }
}
