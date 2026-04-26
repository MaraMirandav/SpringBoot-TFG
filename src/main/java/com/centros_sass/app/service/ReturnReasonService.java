package com.centros_sass.app.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.centros_sass.app.dto.belongings.ReturnReasonRequestDTO;
import com.centros_sass.app.dto.belongings.ReturnReasonResponseDTO;
import com.centros_sass.app.dto.belongings.ReturnReasonUpdateDTO;

public interface ReturnReasonService {

    Page<ReturnReasonResponseDTO> findAll(Pageable pageable);

    Page<ReturnReasonResponseDTO> findAllInactive(Pageable pageable);

    Page<ReturnReasonResponseDTO> findAllIncludingInactive(Pageable pageable);

    Optional<ReturnReasonResponseDTO> findById(Integer id);

    ReturnReasonResponseDTO save(ReturnReasonRequestDTO dto);

    Optional<ReturnReasonResponseDTO> update(Integer id, ReturnReasonUpdateDTO dto);

    Optional<ReturnReasonResponseDTO> delete(Integer id);

}
