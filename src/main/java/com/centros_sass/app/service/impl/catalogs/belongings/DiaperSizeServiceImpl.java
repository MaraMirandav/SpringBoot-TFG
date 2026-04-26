package com.centros_sass.app.service.impl.catalogs.belongings;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.centros_sass.app.dto.catalogs.belongings.DiaperSizeRequestDTO;
import com.centros_sass.app.dto.catalogs.belongings.DiaperSizeResponseDTO;
import com.centros_sass.app.dto.catalogs.belongings.DiaperSizeUpdateDTO;
import com.centros_sass.app.exception.BadRequestException;
import com.centros_sass.app.exception.ResourceNotFoundException;
import com.centros_sass.app.mapper.catalogs.belongings.DiaperSizeMapper;
import com.centros_sass.app.model.catalogs.belongings.DiaperSize;
import com.centros_sass.app.repository.catalogs.belongings.DiaperSizeRepository;
import com.centros_sass.app.service.DiaperSizeService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DiaperSizeServiceImpl implements DiaperSizeService {

    private final DiaperSizeRepository diaperSizeRepository;
    private final DiaperSizeMapper diaperSizeMapper;

    @Override
    @Transactional(readOnly = true)
    public Page<DiaperSizeResponseDTO> findAll(Pageable pageable) {
        return diaperSizeRepository.findAll(pageable)
                .map(diaperSizeMapper::toResponse);
    }



    @Override
    @Transactional(readOnly = true)
    public Optional<DiaperSizeResponseDTO> findById(Integer id) {
        return diaperSizeRepository.findById(id)
                .map(diaperSizeMapper::toResponse);
    }

    @Override
    @Transactional
    public DiaperSizeResponseDTO save(DiaperSizeRequestDTO dto) {
        if (diaperSizeRepository.existsBySize(dto.size())) {
            throw new BadRequestException("Ya existe un tamaño de pañal con el valor: " + dto.size());
        }

        DiaperSize size = diaperSizeMapper.toEntity(dto);
        DiaperSize saved = diaperSizeRepository.save(size);
        return diaperSizeMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public Optional<DiaperSizeResponseDTO> update(Integer id, DiaperSizeUpdateDTO dto) {
        return diaperSizeRepository.findById(id).map(existing -> {
            if (dto.getSize() != null && !dto.getSize().isBlank()) {
                boolean duplicado = diaperSizeRepository.existsBySizeAndIdNot(dto.getSize(), id);
                if (duplicado) {
                    throw new BadRequestException("Ya existe un tamaño de pañal con el valor: " + dto.getSize());
                }
            }

            diaperSizeMapper.updateFromDto(dto, existing);
            DiaperSize saved = diaperSizeRepository.saveAndFlush(existing);
            return diaperSizeMapper.toResponse(saved);
        });
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        DiaperSize entity = diaperSizeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("DiaperSize", "id", id));

        if (!entity.getUserDiapers().isEmpty()) {
            throw new BadRequestException(
                    "No se puede eliminar el tamaño de pañal '" + entity.getSize() + "' porque está en uso");
        }

        diaperSizeRepository.delete(entity);
    }

}
