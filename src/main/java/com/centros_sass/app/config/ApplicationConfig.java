package com.centros_sass.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.centros_sass.app.repository.WorkerRepository;
import com.centros_sass.app.security.WorkerSecurity;

@Configuration
public class ApplicationConfig {

    private final WorkerRepository workerRepository;

    public ApplicationConfig(WorkerRepository workerRepository) {
        this.workerRepository = workerRepository;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> workerRepository.findByEmailWithRoles(username)
                .map(WorkerSecurity::new)
                .orElseThrow(() -> new UsernameNotFoundException("Trabajador no encontrado con email: " + username));
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder);
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
