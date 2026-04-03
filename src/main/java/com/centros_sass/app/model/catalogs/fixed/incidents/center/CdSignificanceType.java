package com.centros_sass.app.model.catalogs.fixed.incidents.center;

import java.util.HashSet;
import java.util.Set;

import com.centros_sass.app.model.base.BaseEntity;
import com.centros_sass.app.model.incidents.center.CenterIncident;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.FetchType;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.NonNull;
import lombok.ToString;

@Entity
@Table(name = "significances_cd_enum")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(onlyExplicitlyIncluded = true, callSuper = false)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class CdSignificanceType extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ToString.Include
    @EqualsAndHashCode.Include
    private Integer id;

    @NonNull
    @Column(name = "significance_name", nullable = false, length = 50, columnDefinition = "VARCHAR", unique = true)
    private String significanceName;

    // RELATIONS
    // // CenterIncident
    @OneToMany(mappedBy = "cdSignificance", fetch = FetchType.LAZY)
    private Set<CenterIncident> centerIncidents = new HashSet<>();

    public void addCenterIncident(CenterIncident centerIncident) {
        centerIncidents.add(centerIncident);
        centerIncident.setCdSignificance(this);
    }
    public void removeCenterIncident(CenterIncident centerIncident) {
        centerIncidents.remove(centerIncident);
        centerIncident.setCdSignificance(null);
    }
}
