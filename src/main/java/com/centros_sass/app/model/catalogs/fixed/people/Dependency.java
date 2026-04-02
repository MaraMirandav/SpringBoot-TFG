package com.centros_sass.app.model.catalogs.fixed.people;

import java.util.HashSet;
import java.util.Set;

import com.centros_sass.app.model.base.BaseEntity;
import com.centros_sass.app.model.profiles.users.User;

import jakarta.persistence.FetchType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "user_dependency_enum")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(onlyExplicitlyIncluded = true, callSuper = false)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class Dependency extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ToString.Include
    @EqualsAndHashCode.Include
    private Integer id;

    @NonNull
    @Column(name = "nivel_dependency", nullable = false, columnDefinition = "TEXT", unique = true)
    private String nivelDependency;

    // RELATIONS
    // // User
    @OneToMany(mappedBy = "dependency", fetch = FetchType.LAZY)
    private Set<User> users = new HashSet<>();

    public void addUser(User user) {
        users.add(user);
        user.setDependency(this);
    }
    public void removeUser(User user) {
        users.remove(user);
        user.setDependency(null);
    }
}
