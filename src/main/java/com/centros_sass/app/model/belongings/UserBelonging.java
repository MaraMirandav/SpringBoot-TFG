package com.centros_sass.app.model.belongings;

import java.io.Serializable;

import com.centros_sass.app.model.base.BaseEntity;
import com.centros_sass.app.model.profiles.users.User;
import com.centros_sass.app.model.profiles.workers.Worker;

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
@Table(name = "user_belongings")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserBelonging extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, columnDefinition = "INTEGER")
    private User user;

    @ManyToOne
    @JoinColumn(name = "user_clothing_id", nullable = false, columnDefinition = "INTEGER")
    private UserClothing userClothing;

    @ManyToOne
    @JoinColumn(name = "user_object_id", nullable = true, columnDefinition = "INTEGER")
    private UserObject userObject;

    @ManyToOne
    @JoinColumn(name = "worker_id", nullable = true, columnDefinition = "INTEGER")
    private Worker worker;

    @Column(name = "comment", columnDefinition = "TEXT")
    private String comment;

    @ManyToOne
    @JoinColumn(name = "user_diaper_id", nullable = true, columnDefinition = "INTEGER")
    private UserDiaper userDiaper;

    @Column(name = "is_request", nullable = false ,columnDefinition = "BOOLEAN")
    private boolean isRequest;

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
        UserBelonging other = (UserBelonging) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "UserBelonging [id=" + id + ", user=" + user + ", userClothes=" + userClothing + ", userObject="
                + userObject + ", worker=" + worker + ", comment=" + comment + ", userDiaper=" + userDiaper
                + ", isRequest=" + isRequest + "]";
    }
}