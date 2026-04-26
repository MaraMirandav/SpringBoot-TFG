package com.centros_sass.app.model.belongings;

import java.util.HashSet;
import java.util.Set;

import com.centros_sass.app.model.base.BaseEntity;
import com.centros_sass.app.model.catalogs.belongings.DiaperSize;
import com.centros_sass.app.model.catalogs.belongings.DiaperType;

import jakarta.persistence.FetchType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "user_diapers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(onlyExplicitlyIncluded = true, callSuper = false)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class UserDiaper extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ToString.Include
    @EqualsAndHashCode.Include
    private Integer id;

    @NonNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diaper_size_id", nullable = false)
    private DiaperSize diaperSize;

    @NonNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diaper_type_id", nullable = false)
    private DiaperType diaperType;

    @NonNull
    @Column(name = "quantity", nullable = false, columnDefinition = "INTEGER")
    private Integer quantity;

    @NonNull
    @Column(name = "is_active", nullable = false, columnDefinition = "BOOLEAN DEFAULT true")
    private Boolean isActive = true;

    // RELATIONS
    // // UserBelonging
    @OneToMany(mappedBy = "userDiaper", fetch = FetchType.LAZY)
    private Set<UserBelonging> userBelongings = new HashSet<>();

    public void addUserBelonging(UserBelonging userBelonging) {
        this.userBelongings.add(userBelonging);
        userBelonging.setUserDiaper(this);
    }

    public void removeUserBelonging(UserBelonging userBelonging) {
        this.userBelongings.remove(userBelonging);
        userBelonging.setUserDiaper(null);
    }
}
