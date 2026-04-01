package com.centros_sass.app.model.profiles.users;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import com.centros_sass.app.model.base.BaseEntity;
import com.centros_sass.app.model.catalogs.fixed.people.Dependency;
import com.centros_sass.app.model.catalogs.fixed.people.Sex;
import com.centros_sass.app.model.treatments.UserMedicalInfo;
import com.centros_sass.app.model.incidents.user.UserIncident;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "users")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ToString.Include
    @EqualsAndHashCode.Include
    private Integer id;

    @NonNull
    @Column(name = "first_name", nullable = false, columnDefinition = "TEXT")
    private String firstName;

    @Column(name = "second_name", columnDefinition = "TEXT")
    private String secondName;

    @NonNull
    @Column(name = "first_surname", nullable = false, columnDefinition = "TEXT")
    private String firstSurname;

    @Column(name = "second_surname", columnDefinition = "TEXT")
    private String secondSurname;

    @Column(name = "alias", columnDefinition = "TEXT")
    private String alias;

    @Column(name = "email", columnDefinition = "TEXT")
    private String email;

    @Column(name = "phone", columnDefinition = "TEXT")
    private String phone;

    @Column(name = "cellphone", columnDefinition = "TEXT")
    private String cellphone;

    @NonNull
    @Column(name = "dni_nie", nullable = false, columnDefinition = "TEXT")
    private String dni;

    @NonNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sex_id", nullable = false)
    private Sex sex;

    @NonNull
    @Column(name = "birth_date", nullable = false, columnDefinition = "DATE")
    private LocalDate birthDate;

    @NonNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dependency_id", nullable = false)
    private Dependency dependency;

    @NonNull
    @Column(name = "is_active", nullable = false, columnDefinition = "BOOLEAN")
    private Boolean isActive;

    // RELATIONS
    // // UserAdress
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private Set<UserAddress> userAdresses = new HashSet<>();

    public void addUserAddress(UserAddress userAddress) {
        userAdresses.add(userAddress);
        userAddress.setUser(this);
    }
    public void removeUserAddress(UserAddress userAddress) {
        userAdresses.remove(userAddress);
        userAddress.setUser(null);
    }

    // // UserAttendanceDay
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private Set<UserAttendanceDay> userAttendanceDays = new HashSet<>();

    public void addUserAttendanceDay(UserAttendanceDay userAttendanceDay) {
        userAttendanceDays.add(userAttendanceDay);
        userAttendanceDay.setUser(this);
    }
    public void removeUserAttendanceDay(UserAttendanceDay userAttendanceDay) {
        userAttendanceDays.remove(userAttendanceDay);
        userAttendanceDay.setUser(null);
    }

    // // UserContact
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private Set<UserContact> userContacts = new HashSet<>();

    public void addUserContact(UserContact userContact) {
        userContacts.add(userContact);
        userContact.setUser(this);
    }
    public void removeUserContact(UserContact userContact) {
        userContacts.remove(userContact);
        userContact.setUser(null);
    }

    // // UserMedicalInfo
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private Set<UserMedicalInfo> userMedicalInfos = new HashSet<>();

    public void addUserMedicalInfo(UserMedicalInfo userMedicalInfo) {
        userMedicalInfos.add(userMedicalInfo);
        userMedicalInfo.setUser(this);
    }

    public void removeUserMedicalInfo(UserMedicalInfo userMedicalInfo) {
        userMedicalInfos.remove(userMedicalInfo);
        userMedicalInfo.setUser(null);
    }

    // // UserIncident
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private Set<UserIncident> userIncidents = new HashSet<>();

    public void addUserIncident(UserIncident userIncident) {
        userIncidents.add(userIncident);
        userIncident.setUser(this);
    }
    public void removeUserIncident(UserIncident userIncident) {
        userIncidents.remove(userIncident);
        userIncident.setUser(null);
    }
}
