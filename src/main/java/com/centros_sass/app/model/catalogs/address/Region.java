package com.centros_sass.app.model.catalogs.address;

import java.util.HashSet;
import java.util.Set;

import com.centros_sass.app.model.base.BaseEntity;
import com.centros_sass.app.model.profiles.users.UserAddress;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "regions_enum")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class Region extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ToString.Include
    @EqualsAndHashCode.Include
    private Integer id;

    @NotBlank(message = "{region.regionName.required}")
    @Column(name = "region_name", nullable = false, columnDefinition = "TEXT", unique = true)
    private String regionName;

    // RELATIONS
    // // UserAdress
    @OneToMany(mappedBy = "region", fetch = FetchType.LAZY)
    private Set<UserAddress> userAdresses = new HashSet<>();
    public void addUserAddress(UserAddress userAddress) {
        userAdresses.add(userAddress);
        userAddress.setRegion(this);
    }
    public void removeUserAddress(UserAddress userAddress) {
        userAdresses.remove(userAddress);
        userAddress.setRegion(null);
    }
}
