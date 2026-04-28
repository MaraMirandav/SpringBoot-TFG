package com.centros_sass.core.security;

import com.centros_sass.app.model.catalogs.organization.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

/**
 * JwtTokenProvider — genera y valida tokens JWT para SaasCon.
 *
 * Reemplaza JwtService (ubicado en app.security).
 *
 * DIFERENCIAS CRÍTICAS frente a JwtService (roto):
 *   1. getSigningKey(): usa jwtSecret.getBytes(UTF_8) — NO Decoders.BASE64.decode()
 *      Esto es CRUCIAL: NimbusJwtDecoder (en SecurityConfig) también usa getBytes(UTF_8).
 *      Si no coinciden, los tokens generados aquí no los puede validar NimbusJwtDecoder.
 *
 *   2. generateToken(): incluye claim "tenant_id" → worker.getTenantId()
 *      Sin este claim, TenantFilter y JwtTenantResolver fallan en cada request.
 *
 *   3. Paquete: core.security (compartido entre módulos) — no app.security
 *
 * FLOW:
 *   AuthServiceImpl.register() / login() → jwtTokenProvider.generateToken(worker)
 *   → JWT string → cliente guarda en localStorage
 *   → cliente envía en header "Authorization: Bearer <token>"
 *   → SecurityConfig (OAuth2ResourceServer) → NimbusJwtDecoder valida la firma
 *   → JwtAuthenticationToken puebla SecurityContext con authorities (roles)
 *   → TenantFilter extrae tenant_id → TenantContext.set()
 */
@Component
public class JwtTokenProvider {

    private static final Logger log = LoggerFactory.getLogger(JwtTokenProvider.class);

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private long jwtExpiration;

    /**
     * Genera un token JWT para un WorkerSecurity (UserDetails envuelve al Worker).
     *
     * Claims incluidos:
     *   - sub: email del worker (subject — identificador único)
     *   - id: ID del worker en BD
     *   - tenant_id: slug del tenant (CRUCIAL — lo lee JwtTenantResolver)
     *   - plan_slug: plan contratado del tenant (basico, profesional, enterprise)
     *   - roles: lista de nombres de rol (ej: ["ROLE_TAS", "ROLE_ADMIN"])
     *   - firstName: nombre del worker
     *   - firstSurname: primer apellido
     *   - iat: issued at (timestamp de creación)
     *   - exp: expiration (timestamp de expiración)
     *
     * @param workerSecurity UserDetails que envuelve el Worker con tenant_id
     * @return token JWT firmado (compact serialization)
     */
    public String generateToken(WorkerSecurity workerSecurity, String planSlug) {
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("id", workerSecurity.getWorker().getId());

        // tenant_id es REQUERIDO — sin esto, TenantFilter falla en cada request
        String tenantId = workerSecurity.getWorker().getTenantId();
        if (tenantId == null || tenantId.isBlank()) {
            throw new IllegalStateException(
                "Worker id=" + workerSecurity.getWorker().getId() + 
                " no tiene tenant_id asignado");
        }
        extraClaims.put("tenant_id", tenantId);

        // plan_slug: opcional, añadido desde TenantProvisioningService
        if (planSlug != null && !planSlug.isBlank()) {
            extraClaims.put("plan_slug", planSlug);
        }

        List<String> roles = workerSecurity.getWorker().getRoles().stream()
                .map(Role::getRoleName)
                .toList();
        extraClaims.put("roles", roles);

        extraClaims.put("firstName", workerSecurity.getWorker().getFirstName());
        extraClaims.put("firstSurname", workerSecurity.getWorker().getFirstSurname());

        return buildToken(extraClaims, workerSecurity.getUsername());
    }

    /**
     * Genera un token JWT para un WorkerSecurity (sin plan_slug).
     *
     * @deprecated Usar generateToken(WorkerSecurity, String) para incluir plan_slug.
     */
    @Deprecated
    public String generateToken(WorkerSecurity workerSecurity) {
        return generateToken(workerSecurity, null);
    }

    /**
     * Genera un token JWT para un AdminUserSecurity (empleado interno de SaasCon).
     *
     * A diferencia de generateToken(WorkerSecurity), este método NO incluye claim "tenant_id"
     * porque los admins internos trabajan en el schema "public" (global), no en un tenant específico.
     *
     * Claims incluidos:
     *   - sub: email del admin (subject — identificador único)
     *   - id: ID del admin en BD
     *   - role: rol del admin (SUPER_ADMIN, ADMIN o SUPPORT)
     *   - fullName: nombre completo del admin
     *   - iat: issued at (timestamp de creación)
     *   - exp: expiration (timestamp de expiración)
     *
     * @param adminSecurity UserDetails que envuelve al AdminUser
     * @return token JWT firmado (compact serialization)
     */
    public String generateAdminToken(AdminUserSecurity adminSecurity) {
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("id", adminSecurity.getAdminUser().getId());
        extraClaims.put("role", adminSecurity.getAdminUser().getRole().name());
        extraClaims.put("fullName", adminSecurity.getAdminUser().getFullName());

        return buildToken(extraClaims, adminSecurity.getUsername());
    }

    /**
     * Construye el token JWT con los claims dados.
     *
     * @param extraClaims claims custom (id, tenant_id, roles, nombres)
     * @param subject email del worker (username)
     * @return token JWT firmado
     */
    private String buildToken(Map<String, Object> extraClaims, String subject) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + jwtExpiration);

        return Jwts.builder()
                .claims(extraClaims)
                .subject(subject)
                .id(UUID.randomUUID().toString())
                .issuedAt(now)
                .expiration(expiration)
                .signWith(getSigningKey())
                .compact();
    }

    /**
     * Extrae el email (subject) del token.
     *
     * @param token JWT string
     * @return email del worker
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Extrae un claim arbitrario del token.
     *
     * @param token JWT string
     * @param claimsResolver función que extrae el campo deseado del payload
     * @return valor del claim
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Verifica que el token es válido para el UserDetails dado.
     *
     * Checks:
     *   1. El subject del token coincide con el username
     *   2. El token no ha expirado
     *
     * @param token JWT string
     * @param workerSecurity UserDetails del worker
     * @return true si el token es válido
     */
    public boolean isTokenValid(String token, WorkerSecurity workerSecurity) {
        final String username = extractUsername(token);
        return username.equals(workerSecurity.getUsername()) && !isTokenExpired(token);
    }

    /**
     * Verifica que el token no ha expirado.
     *
     * @param token JWT string
     * @return true si la fecha de expiración es posterior a ahora
     */
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Extrae la fecha de expiración del token.
     *
     * @param token JWT string
     * @return fecha de expiración
     */
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Parsea el token y extrae todos los claims.
     *
     * Usa la misma clave que NimbusJwtDecoder para que el token generado
     * aquí pueda ser validado por el decoder de Spring Security.
     *
     * @param token JWT string
     * @return todos los claims del token
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    /**
     * Crea la clave de firma HMAC-SHA256.
     *
     * DECISIÓN CRÍTICA DE DISEÑO:
     * ❌ NO usar Decoders.BASE64.decode(jwtSecret)
     *    El old JwtService lo hacía así — y eso ROMPÍA la validación.
     *    Si el secret en application.properties no es BASE64 válido,
     *    Decoders.BASE64.decode() lanza IllegalArgumentException.
     *
     * ✅ Usar jwtSecret.getBytes(StandardCharsets.UTF_8)
     *    Esto coincide con lo que hace NimbusJwtDecoder en SecurityConfig:
     *    new SecretKeySpec(jwtSecret.getBytes(UTF_8), "HmacSHA256")
     *
     *    El secret "cambia-esta-clave-en-produccion-min-32-caracteres!" tiene
     *    exactamente 47 bytes en UTF-8, que superan los 32 bytes mínimos
     *    requeridos para HS256. La clave es suficientemente larga.
     *
     * @return SecretKey para firmar y verificar tokens
     */
    private SecretKey getSigningKey() {
        byte[] keyBytes = jwtSecret.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}

// ─── ¿QUÉ APRENDER DE ESTA CLASE? ──────────────────────────────────────────────────
// 1. Coherencia de clave: getSigningKey() DEBE usar getBytes(UTF_8)
//    porque NimbusJwtDecoder en SecurityConfig usa el mismo método.
//    Usar Decoders.BASE64.decode() rompería la validación del token.
// 2. tenant_id en claims: es OBLIGATORIO — lo extrae JwtTenantResolver
//    en cada request protegido para llamar TenantContext.set().
// 3. Paquete core.security: disponible para todos los módulos
//    (admin, customer, app) — no duplicar lógica de JWT.
// 4. jjwt vs Nimbus: jjwt para GENERAR tokens (necesita la clave secreta),
//    Nimbus para VALIDAR (Spring Security lo hace automáticamente).
// 5. Keys.hmacShaKeyFor(): crea la clave HMAC desde bytes — requiere
//    al menos 32 bytes para HS256.
// ─────────────────────────────────────────────────────────────────────────────