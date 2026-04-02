package com.centros_sass.app.model.belongings;

import java.time.LocalDateTime;

import com.centros_sass.app.model.base.BaseEntity;
import com.centros_sass.app.model.catalogs.dynamic.belongings.ClothingType;
import com.centros_sass.app.model.catalogs.dynamic.belongings.ReturnReason;

import jakarta.persistence.FetchType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.NonNull;
import lombok.ToString;

@Entity
@Table(name = "user_clothing")
@Getter @Setter
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
    @JoinColumn(name = "clothes_id", nullable = false)
    private ClothingType clothes;

    @NonNull
    @Column(name = "is_clean", nullable = false, columnDefinition = "BOOLEAN")
    private Boolean isClean;

    @NonNull
    @Column(name = "is_returned", nullable = false, columnDefinition = "BOOLEAN")
    private Boolean isReturned;

    @NonNull
    @Column(name = "received_at", nullable = false, columnDefinition = "TIMESTAMP")
    private LocalDateTime receivedAt;

    @Column(name = "returned_at", nullable = false, columnDefinition = "TIMESTAMP")
    private LocalDateTime returnedAt;

    @NonNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "return_reason_id", nullable = false)
    private ReturnReason returnReason;

    @NonNull
    @Column(name = "is_active", nullable = false, columnDefinition = "BOOLEAN DEFAULT true")
    private Boolean isActive = true;

}
