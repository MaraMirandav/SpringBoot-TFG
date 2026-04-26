package com.centros_sass.app.controller.profiles;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.centros_sass.app.dto.useraddress.UserAddressRequestDTO;
import com.centros_sass.app.dto.useraddress.UserAddressResponseDTO;
import com.centros_sass.app.dto.useraddress.UserAddressUpdateDTO;
import com.centros_sass.app.exception.ResourceNotFoundException;
import com.centros_sass.app.generic.ApiDataResponse;
import com.centros_sass.app.service.UserAddressService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/user-addresses")
@RequiredArgsConstructor
public class UserAddressController {

    private final UserAddressService userAddressService;

    @GetMapping
    public ResponseEntity<ApiDataResponse<List<UserAddressResponseDTO>>> findAll(
            @PageableDefault(size = 20) Pageable pageable) {
        Page<UserAddressResponseDTO> page = userAddressService.findAll(pageable);
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Direcciones activas encontradas",
                page.getContent(),
                HttpStatus.OK.value(),
                (int) page.getTotalElements()));
    }

    @GetMapping("/inactive")
    public ResponseEntity<ApiDataResponse<List<UserAddressResponseDTO>>> findAllInactive(
            @PageableDefault(size = 20) Pageable pageable) {
        Page<UserAddressResponseDTO> page = userAddressService.findAllInactive(pageable);
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Direcciones inactivas encontradas",
                page.getContent(),
                HttpStatus.OK.value(),
                (int) page.getTotalElements()));
    }

    @GetMapping("/all")
    public ResponseEntity<ApiDataResponse<List<UserAddressResponseDTO>>> findAllIncludingInactive(
            @PageableDefault(size = 20) Pageable pageable) {
        Page<UserAddressResponseDTO> page = userAddressService.findAllIncludingInactive(pageable);
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Todas las direcciones encontradas",
                page.getContent(),
                HttpStatus.OK.value(),
                (int) page.getTotalElements()));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiDataResponse<List<UserAddressResponseDTO>>> findByUserId(@PathVariable Integer userId) {
        List<UserAddressResponseDTO> addresses = userAddressService.findByUserId(userId);
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Direcciones del usuario encontradas",
                addresses,
                HttpStatus.OK.value(),
                addresses.size()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiDataResponse<UserAddressResponseDTO>> findById(@PathVariable Integer id) {
        UserAddressResponseDTO address = userAddressService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("UserAddress", "id", id));
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Dirección encontrada",
                address,
                HttpStatus.OK.value()));
    }

    @PostMapping
    public ResponseEntity<ApiDataResponse<UserAddressResponseDTO>> create(
            @Valid @RequestBody UserAddressRequestDTO dto) {
        UserAddressResponseDTO created = userAddressService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiDataResponse<>(
                        "Dirección creada exitosamente",
                        created,
                        HttpStatus.CREATED.value()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiDataResponse<UserAddressResponseDTO>> update(
            @PathVariable Integer id,
            @Valid @RequestBody UserAddressUpdateDTO dto) {
        UserAddressResponseDTO updated = userAddressService.update(id, dto)
                .orElseThrow(() -> new ResourceNotFoundException("UserAddress", "id", id));
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Dirección actualizada",
                updated,
                HttpStatus.OK.value()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiDataResponse<UserAddressResponseDTO>> delete(@PathVariable Integer id) {
        UserAddressResponseDTO deleted = userAddressService.delete(id)
                .orElseThrow(() -> new ResourceNotFoundException("UserAddress", "id", id));
        return ResponseEntity.ok(new ApiDataResponse<>(
                "Dirección eliminada",
                deleted,
                HttpStatus.OK.value()));
    }
}