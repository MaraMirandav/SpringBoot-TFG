package com.centros_sass.app.model.incidents;

import com.centros_sass.app.model.base.BaseEntity;
import com.centros_sass.app.model.profiles.workers.Worker;

import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@MappedSuperclass
@Getter @Setter
@NoArgsConstructor
public abstract class Comment extends BaseEntity {

    @NonNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "worker_id", nullable = false)
    private Worker worker;

    @NonNull
    @Column(name = "comment", nullable = false, columnDefinition = "TEXT")
    private String comment;
}
