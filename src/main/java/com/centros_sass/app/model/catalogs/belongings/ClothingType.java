package com.centros_sass.app.model.catalogs.belongings;

import java.util.HashSet;
import java.util.Set;

import com.centros_sass.app.model.base.BaseEntity;
import com.centros_sass.app.model.belongings.UserClothing;

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
@Table(name = "clothing_types_enum")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(onlyExplicitlyIncluded = true, callSuper = false)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class ClothingType extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ToString.Include
    @EqualsAndHashCode.Include
    private Integer id;

    @NonNull
    @Column(name = "clothing_name", nullable = false, length = 50, columnDefinition = "VARCHAR", unique = true)
    private String clothingName;

    @NonNull
    @Column(name = "is_active", nullable = false, columnDefinition = "BOOLEAN DEFAULT true")
    private Boolean isActive = true;

    // RELATIONS
    // // UserClothing
    @OneToMany(mappedBy = "clothingType", fetch = FetchType.LAZY)
    private Set<UserClothing> userClothings = new HashSet<>();

    public void addUserClothing(UserClothing userClothing) {
        this.userClothings.add(userClothing);
        userClothing.setClothingType(this);
    }

    public void removeUserClothing(UserClothing userClothing) {
        this.userClothings.remove(userClothing);
        userClothing.setClothingType(null);
    }
}
