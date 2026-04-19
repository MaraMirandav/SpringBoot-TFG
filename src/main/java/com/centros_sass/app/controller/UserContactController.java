package com.centros_sass.app.controller;

import com.centros_sass.app.dto.usercontact.UserContactRequestDTO;
import com.centros_sass.app.dto.usercontact.UserContactResponseDTO;
import com.centros_sass.app.dto.usercontact.UserContactUpdateDTO;
import com.centros_sass.app.exception.ResourceNotFoundException;
import com.centros_sass.app.generic.ApiDataResponse;
import com.centros_sass.app.service.UserContactService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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

import java.util.List;

@RestController
@RequestMapping("/api/v1/user-contacts")
@RequiredArgsConstructor
public class UserContactController {

    private final UserContactService userContactService;

    @GetMapping
    public ResponseEntity<ApiDataResponse<List<UserContactResponseDTO>>> findAll() {
        List<UserContactResponseDTO> lista = userContactService.findAll();
        ApiDataResponse<List<UserContactResponseDTO>> response =
            new ApiDataResponse<>("Contactos encontrados", lista, 200, lista.size());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/all")
    public ResponseEntity<ApiDataResponse<List<UserContactResponseDTO>>> findAllIncludingInactive() {
        List<UserContactResponseDTO> lista = userContactService.findAllIncludingInactive();
        ApiDataResponse<List<UserContactResponseDTO>> response =
            new ApiDataResponse<>("Todos los contactos", lista, 200, lista.size());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiDataResponse<List<UserContactResponseDTO>>> findByUserId(@PathVariable Integer userId) {
        List<UserContactResponseDTO> lista = userContactService.findByUserId(userId);
        ApiDataResponse<List<UserContactResponseDTO>> response =
            new ApiDataResponse<>("Contactos del usuario", lista, 200, lista.size());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/user/{userId}/main")
    public ResponseEntity<ApiDataResponse<UserContactResponseDTO>> findMainContactByUserId(@PathVariable Integer userId) {
        UserContactResponseDTO contacto = userContactService.findMainContactByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("UserContact", "userId", userId));
        ApiDataResponse<UserContactResponseDTO> response =
            new ApiDataResponse<>("Contacto principal del usuario", contacto, 200, 1);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiDataResponse<UserContactResponseDTO>> findById(@PathVariable Integer id) {
        UserContactResponseDTO contacto = userContactService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("UserContact", "id", id));
        ApiDataResponse<UserContactResponseDTO> response =
            new ApiDataResponse<>("Contacto encontrado", contacto, 200, 1);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ApiDataResponse<UserContactResponseDTO>> create(@Valid @RequestBody UserContactRequestDTO dto) {
        UserContactResponseDTO created = userContactService.save(dto);
        ApiDataResponse<UserContactResponseDTO> response =
            new ApiDataResponse<>("Contacto creado", created, 201, 1);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiDataResponse<UserContactResponseDTO>> update(
            @PathVariable Integer id,
            @Valid @RequestBody UserContactUpdateDTO dto) {
        UserContactResponseDTO updated = userContactService.update(id, dto)
                .orElseThrow(() -> new ResourceNotFoundException("UserContact", "id", id));
        ApiDataResponse<UserContactResponseDTO> response =
            new ApiDataResponse<>("Contacto actualizado", updated, 200, 1);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiDataResponse<UserContactResponseDTO>> delete(@PathVariable Integer id) {
        UserContactResponseDTO deleted = userContactService.delete(id)
                .orElseThrow(() -> new ResourceNotFoundException("UserContact", "id", id));
        ApiDataResponse<UserContactResponseDTO> response =
            new ApiDataResponse<>("Contacto eliminado", deleted, 200, 1);
        return ResponseEntity.ok(response);
    }
}