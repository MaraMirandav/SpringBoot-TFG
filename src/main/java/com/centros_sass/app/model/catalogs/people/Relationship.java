package com.centros_sass.app.model.catalogs.people;

import java.util.HashSet;
import java.util.Set;

import com.centros_sass.app.model.base.BaseEntity;
import com.centros_sass.app.model.profiles.users.UserContact;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "user_relationships_enum")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(onlyExplicitlyIncluded = true, callSuper = false)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class Relationship extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ToString.Include
    @EqualsAndHashCode.Include
    private Integer id;

    @NonNull
    @Column(name = "relationship_name", nullable = false, length = 50, columnDefinition = "VARCHAR", unique = true)
    private String relationshipName;

    // RELATIONS
    // // UserContact
    @OneToMany(mappedBy = "contactRelationship", fetch = FetchType.LAZY)
    private Set<UserContact> userContacts = new HashSet<>();

    public void addUserContact(UserContact userContact) {
        userContacts.add(userContact);
        userContact.setContactRelationship(this);
    }
    public void removeUserContact(UserContact userContact) {
        userContacts.remove(userContact);
        userContact.setContactRelationship(null);
    }

}
