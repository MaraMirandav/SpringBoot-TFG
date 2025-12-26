package com.centros_sass.app.model.profiles;

import java.io.Serializable;
import java.time.LocalDate;

import com.centros_sass.app.model.base.BaseEntity;
import com.centros_sass.app.model.catalogs.people.Dependency;
import com.centros_sass.app.model.catalogs.people.Sex;

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
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "first_name", nullable = false, columnDefinition = "TEXT")
    private String firstName;

    @Column(name = "second_name", columnDefinition = "TEXT")
    private String secondName;

    @Column(name = "first_surname", nullable = false, columnDefinition = "TEXT")
    private String firstSurname;

    @Column(name = "second_surname", columnDefinition = "TEXT")
    private String secondSurname;

    @Column(name = "alias", columnDefinition = "TEXT")
    private String alias;

    @Column(name = "dni", nullable = false, columnDefinition = "TEXT")
    private String dni;

    @ManyToOne
    @JoinColumn(name = "sex_id", nullable = false)
    private Sex sex;

    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @ManyToOne
    @JoinColumn(name = "dependency_id", nullable = false)
    private Dependency dependency;

    @Column(name = "is_active", nullable = false)
    private boolean isActive;

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
        User other = (User) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", firstName=" + firstName + ", secondName=" + secondName + ", firstSurname="
                + firstSurname + ", secondSurname=" + secondSurname + ", alias=" + alias + ", dni=" + dni + ", sex="
                + sex + ", birthDate=" + birthDate + ", dependency=" + dependency + ", isActive=" + isActive + "]";
    }

}
