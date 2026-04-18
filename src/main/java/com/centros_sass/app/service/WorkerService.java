package com.centros_sass.app.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.centros_sass.app.dto.worker.WorkerRequestDTO;
import com.centros_sass.app.dto.worker.WorkerResponseDTO;
import com.centros_sass.app.dto.worker.WorkerUpdateDTO;

public interface WorkerService {

    Page<WorkerResponseDTO> findAll(Pageable pageable);

    Page<WorkerResponseDTO> findAllInactive(Pageable pageable);

    Page<WorkerResponseDTO> findAllIncludingInactive(Pageable pageable);

    Optional<WorkerResponseDTO> findById(Integer id);

    WorkerResponseDTO save(WorkerRequestDTO dto);

    Optional<WorkerResponseDTO> update(Integer id, WorkerUpdateDTO dto);

    Optional<WorkerResponseDTO> delete(Integer id);

}
