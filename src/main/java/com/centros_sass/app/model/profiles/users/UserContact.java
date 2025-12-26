package com.centros_sass.app.model.profiles.users;

import java.io.Serializable;

import com.centros_sass.app.model.base.BaseEntity;
import com.centros_sass.app.model.catalogs.people.Relationship;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user_contacts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserContact extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "contact_name", nullable = false, columnDefinition = "TEXT")
    private String contactName;

    @ManyToOne
    @JoinColumn(name = "contact_relationship_id", nullable = false)
    private Relationship contactRelationship;

    @Column(name = "contact_phone", nullable = false, columnDefinition = "TEXT")
    private String contactPhone;

    @Column(name = "contact_email", nullable = false, columnDefinition = "TEXT")
    private String contactEmail;

    @Column(name = "is_contact_main", nullable = false, columnDefinition = "BOOLEAN")
    private boolean isContactMain;

    @Column(name = "contact_note", columnDefinition = "TEXT")
    private String contactNote;

    // hashCode / equals / toString
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        UserContact other = (UserContact) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "UserContact [id=" + id + ", user=" + user + ", contactName=" + contactName + ", contactRelation="
                + contactRelationship + ", contactPhone=" + contactPhone + ", contactEmail=" + contactEmail
                + ", isContactMain=" + isContactMain + ", contactNote=" + contactNote + "]";
    }
}
