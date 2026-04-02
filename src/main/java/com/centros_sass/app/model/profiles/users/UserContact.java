package com.centros_sass.app.model.profiles.users;

import com.centros_sass.app.model.base.BaseEntity;
import com.centros_sass.app.model.catalogs.fixed.people.Relationship;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "user_contacts")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(onlyExplicitlyIncluded = true, callSuper = false)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class UserContact extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ToString.Include
    @EqualsAndHashCode.Include
    private Integer id;

    @NonNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @NonNull
    @Column(name = "contact_name", nullable = false, columnDefinition = "TEXT")
    private String contactName;

    @NonNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contact_relationship_id", nullable = false)
    private Relationship contactRelationship;

    @NonNull
    @Column(name = "contact_phone", nullable = false, columnDefinition = "TEXT")
    private String contactPhone;

    @Column(name = "contact_email", nullable = false, columnDefinition = "TEXT")
    private String contactEmail;

    @Column(name = "is_contact_main", nullable = false, columnDefinition = "BOOLEAN")
    private Boolean isContactMain;

    @Column(name = "contact_note", columnDefinition = "TEXT")
    private String contactNote;

    @NonNull
    @Column(name = "is_active", nullable = false, columnDefinition = "BOOLEAN DEFAULT true")
    private Boolean isActive = true;
}
