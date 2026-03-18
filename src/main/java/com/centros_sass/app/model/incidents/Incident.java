package com.centros_sass.app.model.incidents;

import com.centros_sass.app.model.base.BaseEntity;
import com.centros_sass.app.model.profiles.workers.Worker;

import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@MappedSuperclass
@Getter @Setter
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public abstract class Incident extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ToString.Include
    @EqualsAndHashCode.Include
    private Integer id;

    @NotNull(message = "{incident.createdBy.required}")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by_worker_id", nullable = false)
    private Worker createdBy;

    @NotBlank(message = "{incident.comment.required}")
    @Column(name = "comment", nullable = false, columnDefinition = "TEXT")
    private String comment;

    @NotNull(message = "{incident.status.required}")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "incident_status_id", nullable = false)
    private IncidentStatus incidentStatus;
}
