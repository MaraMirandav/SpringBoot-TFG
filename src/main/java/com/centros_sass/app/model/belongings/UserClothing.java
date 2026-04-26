package com.centros_sass.app.model.belongings;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import com.centros_sass.app.model.base.BaseEntity;
import com.centros_sass.app.model.catalogs.belongings.ClothingType;
import com.centros_sass.app.model.catalogs.belongings.ReturnReason;

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
@Table(name = "user_clothings")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(onlyExplicitlyIncluded = true, callSuper = false)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class UserClothing extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ToString.Include
    @EqualsAndHashCode.Include
    private Integer id;

    @NonNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "clothing_type_id", nullable = false)
    private ClothingType clothingType;

    @NonNull
    @Column(name = "is_clean", nullable = false, columnDefinition = "BOOLEAN")
    private Boolean isClean;

    @NonNull
    @Column(name = "is_returned", nullable = false, columnDefinition = "BOOLEAN")
    private Boolean isReturned;

    @NonNull
    @Column(name = "received_at", nullable = false, columnDefinition = "TIMESTAMP")
    private LocalDateTime receivedAt;

    @Column(name = "returned_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime returnedAt;

    @NonNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "return_reason_id", nullable = false)
    private ReturnReason returnReason;

    @NonNull
    @Column(name = "is_active", nullable = false, columnDefinition = "BOOLEAN DEFAULT true")
    private Boolean isActive = true;

    // RELATIONS
    // // UserBelonging
    @OneToMany(mappedBy = "userClothing", fetch = FetchType.LAZY)
    private Set<UserBelonging> userBelongings = new HashSet<>();

    public void addUserBelonging(UserBelonging userBelonging) {
        this.userBelongings.add(userBelonging);
        userBelonging.setUserClothing(this);
    }

    public void removeUserBelonging(UserBelonging userBelonging) {
        this.userBelongings.remove(userBelonging);
        userBelonging.setUserClothing(null);
    }
}
