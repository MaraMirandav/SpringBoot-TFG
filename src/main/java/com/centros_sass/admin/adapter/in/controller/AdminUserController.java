package com.centros_sass.admin.adapter.in.controller;

import com.centros_sass.admin.adapter.in.dto.AdminUserResponse;
import com.centros_sass.admin.adapter.in.dto.CreateAdminUserRequest;
import com.centros_sass.admin.adapter.in.dto.UpdateAdminUserStatusRequest;
import com.centros_sass.admin.application.service.AdminUserService;
import com.centros_sass.app.generic.ApiDataResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin/users")
@RequiredArgsConstructor
public class AdminUserController {

    private final AdminUserService adminUserService;

    @GetMapping
    public ResponseEntity<ApiDataResponse<Page<AdminUserResponse>>> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        var pageRequest = PageRequest.of(page, size, Sort.by("id").ascending());
        var response = adminUserService.findAll(pageRequest);
        return ResponseEntity.ok(new ApiDataResponse<>("Lista de usuarios admin", response, 200));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiDataResponse<AdminUserResponse>> findById(@PathVariable Long id) {
        var response = adminUserService.findById(id);
        return ResponseEntity.ok(new ApiDataResponse<>("Usuario encontrado", response, 200));
    }

    @PostMapping
    public ResponseEntity<ApiDataResponse<AdminUserResponse>> create(
            @Valid @RequestBody CreateAdminUserRequest request) {
        var response = adminUserService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiDataResponse<>("Usuario admin creado correctamente", response, 201));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<ApiDataResponse<AdminUserResponse>> updateStatus(
            @PathVariable Long id,
            @Valid @RequestBody UpdateAdminUserStatusRequest request) {
        var response = adminUserService.updateStatus(id, request.status());
        return ResponseEntity.ok(new ApiDataResponse<>("Estado actualizado", response, 200));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiDataResponse<Void>> delete(@PathVariable Long id) {
        adminUserService.delete(id);
        return ResponseEntity.ok(new ApiDataResponse<>("Usuario desactivado", null, 200));
    }
}