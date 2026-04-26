package com.centros_sass.app.model.profiles.users;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import com.centros_sass.app.model.base.BaseEntity;
import com.centros_sass.app.model.catalogs.people.Dependency;
import com.centros_sass.app.model.catalogs.people.Sex;
import com.centros_sass.app.model.treatments.UserMedicalInfo;
import com.centros_sass.app.model.bathroom.BathroomSchedule;
import com.centros_sass.app.model.belongings.UserBelonging;
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
@ToString(onlyExplicitlyIncluded = true, callSuper = false)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ToString.Include
    @EqualsAndHashCode.Include
    private Integer id;

    @NonNull
    @Column(name = "first_name", nullable = false, length = 25, columnDefinition = "VARCHAR")
    private String firstName;

    @Column(name = "second_name", length = 25, columnDefinition = "VARCHAR")
    private String secondName;

    @NonNull
    @Column(name = "first_surname", nullable = false, length = 25, columnDefinition = "VARCHAR")
    private String firstSurname;

    @Column(name = "second_surname", length = 25, columnDefinition = "VARCHAR")
    private String secondSurname;

    @Column(name = "alias", length = 25, columnDefinition = "VARCHAR")
    private String alias;

    @Column(name = "email", length = 255, columnDefinition = "VARCHAR")
    private String email;

    @Column(name = "phone", length = 20, columnDefinition = "VARCHAR")
    private String phone;

    @Column(name = "cellphone", length = 20, columnDefinition = "VARCHAR")
    private String cellphone;

    @NonNull
    @Column(name = "dni_nie", nullable = false, length = 9, columnDefinition = "VARCHAR")
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
    @Column(name = "is_active", nullable = false, columnDefinition = "BOOLEAN DEFAULT true")
    private Boolean isActive = true;

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

    // // UserAttendanceRecord
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private Set<UserAttendanceRecord> attendanceRecords = new HashSet<>();

    public void addAttendanceRecord(UserAttendanceRecord record) {
        this.attendanceRecords.add(record);
        record.setUser(this);
    }

    public void removeAttendanceRecord(UserAttendanceRecord record) {
        this.attendanceRecords.remove(record);
        record.setUser(null);
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

    // // BathroomSchedule
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private Set<BathroomSchedule> bathroomSchedules = new HashSet<>();

    public void addBathroomSchedule(BathroomSchedule bathroomSchedule) {
        bathroomSchedules.add(bathroomSchedule);
        bathroomSchedule.setUser(this);
    }

    public void removeBathroomSchedule(BathroomSchedule bathroomSchedule) {
        bathroomSchedules.remove(bathroomSchedule);
        bathroomSchedule.setUser(null);
    }

    // // UserBelonging
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private Set<UserBelonging> userBelongings = new HashSet<>();

    public void addUserBelonging(UserBelonging userBelonging) {
        userBelongings.add(userBelonging);
        userBelonging.setUser(this);
    }

    public void removeUserBelonging(UserBelonging userBelonging) {
        userBelongings.remove(userBelonging);
        userBelonging.setUser(null);
    }
}
