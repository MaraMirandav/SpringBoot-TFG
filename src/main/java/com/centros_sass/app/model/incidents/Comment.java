package com.centros_sass.app.model.incidents;

import com.centros_sass.app.model.base.BaseEntity;
import com.centros_sass.app.model.profiles.workers.Worker;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
// import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

// @MappedSuperclass
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Getter @Setter
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public abstract class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ToString.Include
    @EqualsAndHashCode.Include
    private Integer id;

    @NotNull(message = "{comment.worker.required}")
    @ManyToOne
    @JoinColumn(name = "worker_id", nullable = false)
    private Worker worker;

    @NotBlank(message = "{comment.comment.required}")
    @Column(name = "comment", nullable = false, columnDefinition = "TEXT")
    private String comment;
}
