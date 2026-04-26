package com.centros_sass.app.service.impl.auth;

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
import com.centros_sass.app.repository.catalogs.organization.RoleRepository;
import com.centros_sass.app.repository.profiles.WorkerRepository;
import com.centros_sass.app.security.JwtService;
import com.centros_sass.app.security.WorkerSecurity;
import com.centros_sass.app.service.AuthService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final WorkerRepository workerRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    @Transactional
    public AuthResponse register(RegisterRequest request) {
        String normalizedEmail = request.email().trim().toLowerCase();

        if (workerRepository.existsByEmail(normalizedEmail)) {
            throw new IllegalArgumentException("El email ya está registrado");
        }

        var worker = new Worker();
        worker.setEmail(normalizedEmail);
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

        var defaultRole = roleRepository.findByRoleName("ROLE_TAS")
                .orElseThrow(() -> new IllegalStateException("El rol ROLE_TAS no existe en la base de datos"));
        worker.addRole(defaultRole);
        workerRepository.save(worker);

        var workerSecurity = new WorkerSecurity(worker);
        String token = jwtService.generateToken(workerSecurity);
        return new AuthResponse(token);
    }

    @Override
    @Transactional(readOnly = true)
    public AuthResponse login(LoginRequest request) {
        String normalizedEmail = request.email().trim().toLowerCase();

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(normalizedEmail, request.password())
        );

        var worker = workerRepository.findByEmail(normalizedEmail)
                .orElseThrow(() -> new UsernameNotFoundException("Trabajador no encontrado"));

        var workerSecurity = new WorkerSecurity(worker);
        String token = jwtService.generateToken(workerSecurity);
        return new AuthResponse(token);
    }

}
