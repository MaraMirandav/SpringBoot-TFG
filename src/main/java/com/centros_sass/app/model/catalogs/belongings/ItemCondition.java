package com.centros_sass.app.model.catalogs.belongings;

import java.util.HashSet;
import java.util.Set;

import com.centros_sass.app.model.base.BaseEntity;
import com.centros_sass.app.model.belongings.UserObject;

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
@Table(name = "item_conditions_enum")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(onlyExplicitlyIncluded = true, callSuper = false)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class ItemCondition extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ToString.Include
    @EqualsAndHashCode.Include
    private Integer id;

    @NonNull
    @Column(name = "condition_name", nullable = false, length = 50, columnDefinition = "VARCHAR", unique = true)
    private String conditionName;

    @NonNull
    @Column(name = "is_active", nullable = false, columnDefinition = "BOOLEAN DEFAULT true")
    private Boolean isActive = true;

    // RELATIONS
    // // UserObject
    @OneToMany(mappedBy = "itemCondition", fetch = FetchType.LAZY)
    private Set<UserObject> userObjects = new HashSet<>();

    public void addUserObject(UserObject userObject) {
        this.userObjects.add(userObject);
        userObject.setItemCondition(this);
    }

    public void removeUserObject(UserObject userObject) {
        this.userObjects.remove(userObject);
        userObject.setItemCondition(null);
    }
}
