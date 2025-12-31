package com.centros_sass.app.model.belongings;

import java.io.Serializable;
import java.time.LocalDateTime;

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
@Table(name = "user_clothing")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserClothing extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "clothes_id", nullable = false, columnDefinition = "INTEGER")
    private ClothingType clothes;

    @Column(name = "is_clean", nullable = false, columnDefinition = "BOOLEAN")
    private boolean isClean;

    @Column(name = "is_returned", nullable = false, columnDefinition = "BOOLEAN")
    private boolean isReturned;

    @Column(name = "received_at", nullable = false, columnDefinition = "TIMESTAMP")
    private LocalDateTime receivedAt;

    @Column(name = "returned_at", nullable = false, columnDefinition = "TIMESTAMP")
    private LocalDateTime returnedAt;

    @ManyToOne
    @JoinColumn(name = "return_reason_id", nullable = false, columnDefinition = "INTEGER")
    private ReturnReason returnReason;

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
        UserClothing other = (UserClothing) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "UserClothes [id=" + id + ", clothes=" + clothes + ", isClean=" + isClean + ", isReturned=" + isReturned
                + ", receivedAt=" + receivedAt + ", returnedAt=" + returnedAt + ", returnReason=" + returnReason + "]";
    }
}
