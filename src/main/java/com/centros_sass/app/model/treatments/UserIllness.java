package com.centros_sass.app.model.treatments;

import java.util.HashSet;
import java.util.Set;

import com.centros_sass.app.model.base.BaseEntity;
import com.centros_sass.app.model.catalogs.dynamic.treatments.Illness;

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
@Table(name = "user_illnesses")
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class UserIllness extends BaseEntity {

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
    @JoinColumn(name = "illness_id", nullable = false)
    private Illness illness;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "user_illness_treatment_details",
        joinColumns = @JoinColumn(name = "user_illness_id"),
        inverseJoinColumns = @JoinColumn(name = "treatment_detail_id")
    )
    private Set<TreatmentDetail> treatmentDetails = new HashSet<>();

    public void addTreatmentDetail(TreatmentDetail treatmentDetail) {
        treatmentDetails.add(treatmentDetail);
        treatmentDetail.getUserIllnesses().add(this);
    }

    public void removeTreatmentDetail(TreatmentDetail treatmentDetail) {
        treatmentDetails.remove(treatmentDetail);
        treatmentDetail.getUserIllnesses().remove(this);
    }

    @NonNull
    @Column(name = "is_active", nullable = false, columnDefinition = "BOOLEAN DEFAULT true")
    private Boolean isActive = true;
}
