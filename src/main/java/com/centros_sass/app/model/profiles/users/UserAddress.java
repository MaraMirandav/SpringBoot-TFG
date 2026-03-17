package com.centros_sass.app.model.profiles.users;

import com.centros_sass.app.model.base.BaseEntity;
import com.centros_sass.app.model.catalogs.address.City;
import com.centros_sass.app.model.catalogs.address.Province;
import com.centros_sass.app.model.catalogs.address.Region;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "user_addresses")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class UserAddress extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ToString.Include
    @EqualsAndHashCode.Include
    private Integer id;

    @NotNull(message = "{userAddress.user.required}")
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @NotBlank(message = "{userAddress.address.required}")
    @Column(name = "address", nullable = false, columnDefinition = "TEXT")
    private String address;

    @NotBlank(message = "{userAddress.postalCode.required}")
    @Pattern(regexp = "^\\d{5}$", message = "{userAddress.postalCode.invalid}") // Esta bien escapado?
    @Column(name = "postal_code", nullable = false, columnDefinition = "TEXT")
    private String postalCode;

    @NotNull(message = "{userAddress.city.required}")
    @ManyToOne
    @JoinColumn(name = "city_id", nullable = false)
    private City city;

    @NotNull(message = "{userAddress.province.required}")
    @ManyToOne
    @JoinColumn(name = "province_id", nullable = false)
    private Province province;

    @NotNull(message = "{userAddress.region.required}")
    @ManyToOne
    @JoinColumn(name = "region_id", nullable = false)
    private Region region;

}
