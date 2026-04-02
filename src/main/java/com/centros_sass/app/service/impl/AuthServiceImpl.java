package com.centros_sass.app.service.impl;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.centros_sass.app.dto.auth.AuthResponse;
import com.centros_sass.app.dto.auth.LoginRequest;
import com.centros_sass.app.dto.auth.RegisterRequest;
import com.centros_sass.app.model.profiles.workers.Worker;
import com.centros_sass.app.repository.WorkerRepository;
import com.centros_sass.app.security.JwtService;
import com.centros_sass.app.security.WorkerSecurity;
import com.centros_sass.app.service.AuthService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final WorkerRepository workerRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    @Transactional
    public AuthResponse register(RegisterRequest request) {
        if (workerRepository.existsByEmail(request.email())) {
            throw new IllegalArgumentException("El email ya está registrado");
        }

        var worker = new Worker();
        worker.setEmail(request.email());
        worker.setPassword(passwordEncoder.encode(request.password()));
        worker.setFirstName(request.firstName());
        worker.setSecondName(request.secondName());
        worker.setFirstSurname(request.firstSurname());
        worker.setSecondSurname(request.secondSurname());
        worker.setDni(request.dni());
        worker.setMainPhone(request.mainPhone());
        worker.setSecondPhone(request.secondPhone());
        worker.setIsActive(true);

        workerRepository.save(worker);

        var workerSecurity = new WorkerSecurity(worker);
        var token = jwtService.generateToken(workerSecurity);
        return new AuthResponse(token);
    }

    @Override
    @Transactional(readOnly = true)
    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password())
        );

        var worker = workerRepository.findByEmail(request.email())
                .orElseThrow(() -> new UsernameNotFoundException("Trabajador no encontrado"));

        var workerSecurity = new WorkerSecurity(worker);
        var token = jwtService.generateToken(workerSecurity);
        return new AuthResponse(token);
    }

}
