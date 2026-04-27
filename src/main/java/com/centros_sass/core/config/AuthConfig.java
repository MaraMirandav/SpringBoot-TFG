package com.centros_sass.core.config;

import com.centros_sass.app.model.profiles.workers.Worker;
import com.centros_sass.app.repository.WorkerRepository;
import com.centros_sass.core.security.WorkerSecurity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * AuthConfig — configuración centralizada de autenticación para SaasCon.
 *
 * Centraliza TRES beans críticos que antes estaban dispersos:
 *   1. UserDetailsService: carga el Worker desde la BD para autenticar
 *   2. AuthenticationProvider: valida password contra BCrypt hash en BD
 *   3. AuthenticationManager: punto de entrada para AuthenticationManager.authenticate()
 *
 * DECISIÓN CLAVE: PasswordEncoder NO se define aquí.
 * Ya existe el bean "passwordEncoder" en SecurityConfig con BCrypt 12.
 * Aquí solo referenciamos ese bean por nombre en el AuthenticationProvider.
 * Esto evita duplicar la configuración de encoding.
 */
@Configuration
public class AuthConfig {

    private static final Logger log = LoggerFactory.getLogger(AuthConfig.class);

    private final WorkerRepository workerRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Constructor injection — estándar de Spring Boot 3.
     *
     * @param workerRepository acceso a la tabla de workers (JPA)
     * @param passwordEncoder referencia al bean BCrypt de SecurityConfig
     */
    public AuthConfig(WorkerRepository workerRepository, PasswordEncoder passwordEncoder) {
        this.workerRepository = workerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * UserDetailsService — carga un Worker desde la BD dado su email.
     *
     * Spring Security llama este método durante authenticationManager.authenticate()
     * para obtener el UserDetails ( WorkerSecurity ) con los authorities (roles).
     *
     * USOS:
     * - AuthServiceImpl.login(): authenticationManager.authenticate(...) → internamente usa esto
     * - JwtTenantResolver: para extraer tenant_id del worker ya cargado
     *
     * @return UserDetailsService que envuelve un Worker en WorkerSecurity
     */
    @Bean
    public UserDetailsService userDetailsService() {
        return email -> {
            log.debug("Autenticando usuario...");
            Worker worker = workerRepository.findByEmailWithRoles(email)
                    .orElseThrow(() -> new UsernameNotFoundException(
                            "Worker no encontrado con email: " + email));

            return new WorkerSecurity(worker);
        };
    }

    /**
     * AuthenticationProvider — combina UserDetailsService + PasswordEncoder.
     *
     * DaoAuthenticationProvider es la implementación estándar de Spring Security
     * que valida el password enviado por el usuario contra el hash BCrypt en la BD.
     *
     * DECISIONES:
     * - Constructor con UserDetailsService: quién carga el usuario (WorkerSecurity)
     * - setPasswordEncoder(): cómo verificar el password (BCrypt 12)
     *
     * NOTA: En Spring Security 7.x, DaoAuthenticationProvider usa constructor
     * con UserDetailsService como único parámetro. Los setters ya no existen.
     *
     * @return AuthenticationProvider configurado listo para AuthenticationManager
     */
    @Bean
    public AuthenticationProvider authenticationProvider(UserDetailsService userDetailsService) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }

    /**
     * AuthenticationManager — punto de entrada para autenticar credenciales.
     *
     * AuthenticationConfiguration.getAuthenticationManager() extrae el AuthenticationManager
     * que Spring Boot construye automáticamente desde los AuthenticationProviders registrados.
     *
     * USO PRINCIPAL:
     * - AuthServiceImpl.login(): authenticationManager.authenticate(
     *       new UsernamePasswordAuthenticationToken(email, password))
     *
     * @return AuthenticationManager listo para usar
     * @throws Exception si Spring no puede construir el AuthenticationManager
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}

// ─── ¿QUÉ APRENDER DE ESTA CLASE? ────────────────────────────────────────────
// 1. PasswordEncoder NO se duplica — se referencia del bean existente en SecurityConfig
// 2. @Bean en método: permite inyección limpia sin anotaciones de campo
// 3. DaoAuthenticationProvider: implementación estándar que maneja todo el flujo
//    auth → UserDetailsService.loadUserByUsername() → PasswordEncoder.matches()
// 4. @Configuration vs @Component: ambos funcionan, @Configuration indica
//    que esta clase es pura configuración de Spring (no es un servicio)
// 5. El log NO incluye email — es PII (Personally Identifiable Information)
// ──────────────────────────────────────────────────────────────────────────────