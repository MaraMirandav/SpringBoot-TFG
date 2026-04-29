package com.centros_sass.customer.adapter.in.controller;

import com.centros_sass.customer.adapter.in.dto.CustomerRegisterRequest;
import com.centros_sass.customer.adapter.in.dto.CustomerRegisterResponse;
import com.centros_sass.customer.application.service.CustomerRegistrationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerRegistrationService customerRegistrationService;

    @PostMapping("/register")
    public ResponseEntity<CustomerRegisterResponse> register(
            @Valid @RequestBody CustomerRegisterRequest request,
            HttpServletRequest httpRequest) {
        var response = customerRegistrationService.register(request, httpRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}