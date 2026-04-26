package com.centros_sass.app.service.impl.profiles;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.centros_sass.app.dto.useraddress.UserAddressRequestDTO;
import com.centros_sass.app.dto.useraddress.UserAddressResponseDTO;
import com.centros_sass.app.dto.useraddress.UserAddressUpdateDTO;
import com.centros_sass.app.mapper.profiles.UserAddressMapper;
import com.centros_sass.app.model.catalogs.address.City;
import com.centros_sass.app.model.catalogs.address.Province;
import com.centros_sass.app.model.catalogs.address.Region;
import com.centros_sass.app.model.profiles.users.User;
import com.centros_sass.app.model.profiles.users.UserAddress;
import com.centros_sass.app.repository.catalogs.address.CityRepository;
import com.centros_sass.app.repository.catalogs.address.ProvinceRepository;
import com.centros_sass.app.repository.catalogs.address.RegionRepository;
import com.centros_sass.app.repository.profiles.UserAddressRepository;
import com.centros_sass.app.repository.profiles.UserRepository;
import com.centros_sass.app.service.UserAddressService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserAddressServiceImpl implements UserAddressService {

    private final UserAddressRepository userAddressRepository;
    private final UserRepository userRepository;
    private final RegionRepository regionRepository;
    private final ProvinceRepository provinceRepository;
    private final CityRepository cityRepository;
    private final UserAddressMapper userAddressMapper;

    @Override
    @Transactional(readOnly = true)
    public Page<UserAddressResponseDTO> findAll(Pageable pageable) {
        return userAddressRepository.findByIsActiveTrue(pageable)
                .map(userAddressMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserAddressResponseDTO> findAllInactive(Pageable pageable) {
        return userAddressRepository.findByIsActiveFalse(pageable)
                .map(userAddressMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserAddressResponseDTO> findAllIncludingInactive(Pageable pageable) {
        return userAddressRepository.findAll(pageable)
                .map(userAddressMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UserAddressResponseDTO> findById(Integer id) {
        return userAddressRepository.findById(id)
                .map(userAddressMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserAddressResponseDTO> findByUserId(Integer userId) {
        return userAddressRepository.findByUserIdAndIsActiveTrue(userId)
                .stream()
                .map(userAddressMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public UserAddressResponseDTO save(UserAddressRequestDTO dto) {
        User user = userRepository.findById(dto.userId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + dto.userId()));

        Region region = regionRepository.findById(dto.regionId())
                .orElseThrow(() -> new RuntimeException("Región no encontrada con id: " + dto.regionId()));

        Province province = provinceRepository.findById(dto.provinceId())
                .orElseThrow(() -> new RuntimeException("Provincia no encontrada con id: " + dto.provinceId()));

        City city = cityRepository.findById(dto.cityId())
                .orElseThrow(() -> new RuntimeException("Ciudad no encontrada con id: " + dto.cityId()));

        UserAddress entity = userAddressMapper.toEntity(dto);
        entity.setUser(user);
        entity.setRegion(region);
        entity.setProvince(province);
        entity.setCity(city);

        UserAddress saved = userAddressRepository.save(entity);
        return userAddressMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public Optional<UserAddressResponseDTO> update(Integer id, UserAddressUpdateDTO dto) {
        return userAddressRepository.findById(id)
                .map(existing -> {
                    if (dto.getAddress() != null) {
                        existing.setAddress(dto.getAddress());
                    }
                    if (dto.getPostalCode() != null) {
                        existing.setPostalCode(dto.getPostalCode());
                    }
                    if (dto.getRegionId() != null) {
                        Region region = regionRepository.findById(dto.getRegionId())
                                .orElseThrow(() -> new RuntimeException("Región no encontrada con id: " + dto.getRegionId()));
                        existing.setRegion(region);
                    }
                    if (dto.getProvinceId() != null) {
                        Province province = provinceRepository.findById(dto.getProvinceId())
                                .orElseThrow(() -> new RuntimeException("Provincia no encontrada con id: " + dto.getProvinceId()));
                        existing.setProvince(province);
                    }
                    if (dto.getCityId() != null) {
                        City city = cityRepository.findById(dto.getCityId())
                                .orElseThrow(() -> new RuntimeException("Ciudad no encontrada con id: " + dto.getCityId()));
                        existing.setCity(city);
                    }
                    if (dto.getIsActive() != null) {
                        existing.setIsActive(dto.getIsActive());
                    }

                    UserAddress saved = userAddressRepository.save(existing);
                    return userAddressMapper.toResponse(saved);
                });
    }

    @Override
    @Transactional
    public Optional<UserAddressResponseDTO> delete(Integer id) {
        return userAddressRepository.findById(id)
                .map(existing -> {
                    existing.setIsActive(false);
                    UserAddress saved = userAddressRepository.save(existing);
                    return userAddressMapper.toResponse(saved);
                });
    }
}