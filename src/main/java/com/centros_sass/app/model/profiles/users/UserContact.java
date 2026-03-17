package com.centros_sass.app.model.profiles.users;

import com.centros_sass.app.model.base.BaseEntity;
import com.centros_sass.app.model.catalogs.people.Relationship;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "user_contacts")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class UserContact extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ToString.Include
    @EqualsAndHashCode.Include
    private Integer id;

    @NotNull(message = "{userContact.user.required}")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @NotBlank(message = "{userContact.contactName.required}")
    @Column(name = "contact_name", nullable = false, columnDefinition = "TEXT")
    private String contactName;

    @NotNull(message = "{userContact.contactRelationship.required}")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contact_relationship_id", nullable = false)
    private Relationship contactRelationship;

    @NotBlank(message = "{userContact.contactPhone.required}")
    @Column(name = "contact_phone", nullable = false, columnDefinition = "TEXT")
    private String contactPhone;

    @NotBlank(message = "{userContact.contactEmail.required}")
    @Email(message = "{userContact.contactEmail.invalid}")
    @Column(name = "contact_email", nullable = false, columnDefinition = "TEXT")
    private String contactEmail;

    @NotNull(message = "{userContact.isContactMain.required}")
    @Column(name = "is_contact_main", nullable = false, columnDefinition = "BOOLEAN")
    private Boolean isContactMain;

    @Column(name = "contact_note", columnDefinition = "TEXT")
    private String contactNote;
}
