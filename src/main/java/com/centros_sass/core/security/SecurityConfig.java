package com.centros_sass.core.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * SecurityConfig — configuración central de Spring Security para SaasCon.
 *
 * Define TRES zonas de acceso:
 *   1. Pública:  /api/v1/auth/** y /actuator/** → sin autenticación
 *   2. Admin:    /api/v1/admin/** → requiere rol ADMIN en el JWT
 *   3. Resto:    cualquier otra ruta → requiere JWT válido (cualquier rol)
 *
 * Usa OAuth2 Resource Server: Spring Security valida el JWT automáticamente
 * en cada request antes de llegar al controller. Si el token es inválido,
 * devuelve 401 sin que ningún controller se ejecute.
 *
 * Sin estado (STATELESS): no hay sesiones HTTP. Cada request debe traer su JWT.
 * Esto es esencial en APIs REST y en arquitecturas multitenancy.
 */
@Configuration
@EnableWebSecurity
// @EnableMethodSecurity habilita @PreAuthorize en métodos de servicio/controller
// Ejemplo: @PreAuthorize("hasRole('ADMIN')") en un método específico
@EnableMethodSecurity
public class SecurityConfig {

    // jwt.secret viene de application.properties — la misma clave que usa JwtTokenProvider
    // @Value inyecta el valor en el momento de instanciar el bean
    @Value("${jwt.secret}")
    private String jwtSecret;

    /**
     * Define las reglas de seguridad HTTP para toda la aplicación.
     *
     * Spring Security aplica estas reglas en orden: el primer requestMatcher que
     * coincide define el comportamiento. Por eso las rutas específicas van primero.
     *
     * @param http   builder de Spring Security — se configura encadenando métodos
     * @param jwtDecoder bean que valida y decodifica tokens JWT (definido abajo)
     * @return la cadena de filtros de seguridad configurada
     */
    /**
     * Define las reglas de seguridad HTTP para toda la aplicación.
     *
     * Spring Security aplica estas reglas en orden: el primer requestMatcher que
     * coincide define el comportamiento. Por eso las rutas específicas van primero.
     *
     * @param http                   builder de Spring Security — se configura encadenando métodos
     * @param jwtDecoder             bean que valida y decodifica tokens JWT (definido abajo)
     * @param authenticationProvider proveedor que carga el UserDetails y verifica la contraseña.
     *                               Necesario para que el login con username/password funcione
     *                               en paralelo con la validación JWT de OAuth2ResourceServer.
     * @return la cadena de filtros de seguridad configurada
     */
    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            JwtDecoder jwtDecoder,
            AuthenticationProvider authenticationProvider) throws Exception {
        http
                // Desactivar CSRF — en APIs REST stateless no hay sesiones que proteger.
                // CSRF protege formularios web con cookies; JWT en Authorization header no lo necesita.
                .csrf(csrf -> csrf.disable())

                // Habilitar CORS para permitir peticiones cross-origin
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))

                // STATELESS: Spring Security no crea ni usa HttpSession.
                // Cada request es independiente y se autentica solo con el JWT.
                // Sin esto, Spring crearía sesiones → rompería la arquitectura REST.
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // ── Reglas de autorización (en orden de especificidad) ──────────────
                .authorizeHttpRequests(auth -> auth

                        // Rutas PÚBLICAS — no requieren token JWT
                        // /api/v1/auth/**: login, refresh token, registro (versión v1)
                        // /api/auth/**: login, refresh token, registro (versión legacy)
                        // /api/v1/admin/auth/**: login de admins internos (sin JWT todavía)
                        // /actuator/**: health check, métricas (para load balancers y k8s)
                        // /swagger-ui/**, /v3/api-docs/**, /swagger-ui.html: OpenAPI docs
                        .requestMatchers(
                                "/api/v1/auth/**",
                                "/api/auth/**",
                                "/api/v1/admin/auth/**",
                                "/actuator/**",
                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/swagger-ui.html",
                                "/v3/api-docs.yaml"
                        ).permitAll()

                        // Rutas ADMIN — cualquier rol de admin puede acceder
                        // Los tres roles: SUPER_ADMIN, ADMIN, SUPPORT
                        // JwtAuthenticationConverter convierte el claim "role" a authorities
                        .requestMatchers("/api/v1/admin/**").hasAnyRole("ADMIN", "SUPER_ADMIN", "SUPPORT")

                        // CUALQUIER OTRA RUTA — debe estar autenticado (JWT válido)
                        // No importa el rol — solo que el token sea válido y no haya expirado
                        .anyRequest().authenticated()
                )

                // ── OAuth2 Resource Server — validación automática de JWT ───────────
                // Spring Security intercepta el header "Authorization: Bearer <token>"
                // y llama al JwtDecoder para validar la firma y la expiración.
                // Si el token es inválido → 401 Unauthorized automáticamente.
                // Si el token es válido → puebla el SecurityContext con el Authentication.
                // JwtAuthenticationConverter extrae authorities del claim "role"
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt
                                .decoder(jwtDecoder)
                                .jwtAuthenticationConverter(jwtAuthenticationConverter()))
                )

                // Registra el proveedor que verifica credenciales (username + password hasheado).
                // Sin esto, el AuthenticationManager no sabe cómo autenticar en el endpoint /auth/login.
                .authenticationProvider(authenticationProvider);

        return http.build();
    }

    /**
     * Configura el decodificador JWT para clave simétrica (HMAC-SHA256).
     *
     * Usamos HMAC (clave simétrica) porque firmamos y verificamos con la MISMA clave.
     * La alternativa es RSA (asimétrica): clave privada para firmar, pública para verificar.
     * HMAC es más simple para aplicaciones monolíticas; RSA es mejor para microservicios
     * donde diferentes servicios solo necesitan verificar, no firmar.
     *
     * @return decodificador que verifica tokens HMAC-SHA256 con el mismo secret
     */
    @Bean
    public JwtDecoder jwtDecoder() {
        // SecretKeySpec crea una clave criptográfica desde los bytes del string
        // "HmacSHA256" indica el algoritmo — debe coincidir con el usado en JwtTokenProvider
        SecretKey key = new SecretKeySpec(jwtSecret.getBytes(StandardCharsets.UTF_8), "HmacSHA256");

        // NimbusJwtDecoder es la implementación de Spring Security basada en Nimbus-JOSE-JWT
        // withSecretKey() → configura para clave simétrica (HMAC)
        // build() → construye el decoder listo para usar
        return NimbusJwtDecoder.withSecretKey(key).build();
    }

    /**
     * JwtAuthenticationConverter — extrae authorities del claim "role" en el JWT.
     *
     * El JWT del admin contiene claim "role": "SUPER_ADMIN" (sin prefijo ROLE_).
     * Spring Security espera authorities con prefijo "ROLE_" para hasRole() funcione.
     *
     * JwtGrantedAuthoritiesConverter:
     *   - authoritiesClaimName("role") → lee el claim "role"
     *   - authorityPrefix("ROLE_") → añade prefijo "ROLE_" al valor
     *
     * Esto permite usar hasAnyRole("ADMIN", "SUPER_ADMIN", "SUPPORT").
     *
     * @return converter configurado
     */
    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        grantedAuthoritiesConverter.setAuthoritiesClaimName("role");
        grantedAuthoritiesConverter.setAuthorityPrefix("ROLE_");

        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverter);
        return jwtAuthenticationConverter;
    }

    /**
     * Encoder de contraseñas BCrypt con work factor 12.
     *
     * BCrypt aplica hashing con sal aleatoria y es resistente a ataques de fuerza bruta.
     * Work factor 12 significa 2^12 = 4096 iteraciones por hash (más = más lento = más seguro).
     * El estándar mínimo de la industria en 2024 es work factor 12.
     *
     * @return encoder que hashea contraseñas con BCrypt
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOriginPatterns(List.of("*"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
        config.setAllowedHeaders(List.of("*"));
        config.setExposedHeaders(List.of("Authorization"));
        config.setAllowCredentials(true);
        config.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}

// ─── ¿QUÉ APRENDER DE ESTA CLASE? ────────────────────────────────────────────
// 1. SecurityFilterChain: define reglas HTTP en vez de extender WebSecurityConfigurerAdapter
//    (patrón moderno desde Spring Security 5.7 — el adapter está deprecado)
// 2. STATELESS vs STATEFUL: APIs REST son stateless → no hay sesiones → JWT en cada request
// 3. CSRF: solo importa con sesiones/cookies; JWT en header Authorization no necesita CSRF
// 4. Orden de requestMatchers: de MÁS específico a MENOS — "public antes que authenticated"
// 5. HMAC vs RSA: simétrica (misma clave) para monolito; asimétrica para microservicios
// 6. OAuth2ResourceServer: Spring intercepta el JWT automáticamente → no necesitas JwtFilter manual
// 7. @EnableMethodSecurity: permite seguridad a nivel de método con @PreAuthorize
// 8. allowedOriginPatterns vs allowedOrigins: usar siempre allowedOriginPatterns
//    cuando allowCredentials=true — allowedOrigins("*") con credentials es inválido
//    según la spec CORS y los browsers lo rechazan con error
// 9. setMaxAge(3600L): cachea el preflight OPTIONS 1 hora — sin esto el browser
//    hace un request OPTIONS antes de CADA petición POST/PUT/DELETE → 2x latencia
// ─────────────────────────────────────────────────────────────────────────────
