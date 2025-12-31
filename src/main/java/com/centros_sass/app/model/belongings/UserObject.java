package com.centros_sass.app.model.belongings;

import java.io.Serializable;

import com.centros_sass.app.model.base.BaseEntity;

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
@Table(name = "user_objects")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserObject extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "condition_id", nullable = false, columnDefinition = "INTEGER")
    private ItemCondition condition;

    @ManyToOne
    @JoinColumn(name = "object_id", nullable = false, columnDefinition = "INTEGER")
    private ObjectType object;

    @Column(name = "comment", columnDefinition = "TEXT")
    private String comment;

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
        UserObject other = (UserObject) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "UserObject [id=" + id + ", condition=" + condition + ", object=" + object + ", comment=" + comment
                + "]";
    }
}
