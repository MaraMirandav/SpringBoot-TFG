package com.centros_sass.app.model.catalogs.belongings;

import java.util.HashSet;
import java.util.Set;

import com.centros_sass.app.model.base.BaseEntity;
import com.centros_sass.app.model.belongings.UserDiaper;

import jakarta.persistence.FetchType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "diaper_types_enum")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(onlyExplicitlyIncluded = true, callSuper = false)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class DiaperType extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ToString.Include
    @EqualsAndHashCode.Include
    private Integer id;

    @NonNull
    @Column(name = "type", nullable = false, length = 50, columnDefinition = "VARCHAR", unique = true)
    private String type;

    @NonNull
    @Column(name = "is_active", nullable = false, columnDefinition = "BOOLEAN DEFAULT true")
    private Boolean isActive = true;

    // RELATIONS
    // // UserDiaper
    @OneToMany(mappedBy = "diaperType", fetch = FetchType.LAZY)
    private Set<UserDiaper> userDiapers = new HashSet<>();

    public void addUserDiaper(UserDiaper userDiaper) {
        this.userDiapers.add(userDiaper);
        userDiaper.setDiaperType(this);
    }

    public void removeUserDiaper(UserDiaper userDiaper) {
        this.userDiapers.remove(userDiaper);
        userDiaper.setDiaperType(null);
    }
}
