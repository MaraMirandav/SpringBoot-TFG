# Skill: Security — JWT, MapStruct, Validation, Auditing

---

## 1. MapStruct — OBLIGATORIO para entity ↔ DTO

### Regla: NUNCA exponer @Entity en responses ni aceptarlos como @RequestBody

```java
// Entity — uso interno únicamente
@Entity
@Table(name = "tenants")
@EntityListeners(AuditingEntityListener.class)
public class TenantEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String slug;
    private String adminEmail;
    private TenantStatus status;

    @CreatedDate  private Instant createdAt;
    @LastModifiedDate private Instant updatedAt;
}

// Request DTO — lo que entra por la API
public record CreateTenantRequest(
    @NotBlank @Size(max = 100)                   String name,
    @NotBlank @Pattern(regexp = "^[a-z0-9-]+$") String slug,
    @NotBlank @Email                             String adminEmail
) {}

// Response DTO — lo que sale por la API
public record TenantResponse(
    Long id,
    String name,
    String slug,
    String adminEmail,
    String status,
    Instant createdAt
) {}

// Mapper — MapStruct genera la implementación en compile time
@Mapper(componentModel = "spring")
public interface TenantMapper {
    TenantResponse toResponse(TenantEntity entity);
    TenantEntity   toEntity(CreateTenantRequest request);
}
```

```java
// ✅ CORRECTO
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/tenants")
public class TenantController {
    private final CreateTenantUseCase createTenantUseCase;

    @PostMapping
    public ResponseEntity<TenantResponse> create(@Valid @RequestBody CreateTenantRequest request) {
        return ResponseEntity.status(201).body(createTenantUseCase.execute(request));
    }
}

// ❌ PROHIBIDO — nunca hacer esto
@GetMapping
public List<TenantEntity> getAll() { return tenantRepository.findAll(); } // NUNCA
```

---

## 2. Bean Validation — en TODOS los request DTOs

```java
public record CreateTenantRequest(

    @NotBlank(message = "Name is required")
    @Size(max = 100, message = "Name must be at most 100 characters")
    String name,

    @NotBlank(message = "Slug is required")
    @Pattern(regexp = "^[a-z0-9-]+$", message = "Slug must be lowercase letters, numbers, and hyphens only")
    @Size(min = 3, max = 50)
    String slug,

    @NotBlank(message = "Admin email is required")
    @Email(message = "Invalid email format")
    String adminEmail
) {}
```

### Anotaciones requeridas

| Anotación    | Uso                                         |
|--------------|---------------------------------------------|
| `@NotNull`   | Campo no puede ser null                     |
| `@NotBlank`  | String no puede ser vacío o solo espacios   |
| `@NotEmpty`  | Collection/String no puede estar vacía      |
| `@Email`     | Formato de email válido                     |
| `@Size`      | Longitud mínima/máxima de string/collection |
| `@Min/@Max`  | Límites numéricos                           |
| `@Pattern`   | Validación con regex                        |
| `@Valid`     | Validar objetos anidados                    |

---

## 3. AuditService

# ════════════════════════════════════════════════════════════════
# ✅ CORRECCIÓN #1 — TenantContext inyectado como bean (INCORRECTO)
#
# ORIGINAL (roto):
#   private final TenantContext tenantContext;    ← CAMPO INYECTADO
#   tenantContext.getCurrentTenant()             ← LLAMADA DE INSTANCIA
#
# TenantContext es una CLASE DE UTILIDAD ESTÁTICA — no tiene constructor
# público, no tiene @Component, y no puede ser un Spring bean.
# Spring lanzaría NoSuchBeanDefinitionException al arrancar.
#
# CORRECCIÓN:
#   Usar TenantContext.get() como llamada estática directamente.
#   Igual que se usa Math.abs() o Collections.emptyList().
# ════════════════════════════════════════════════════════════════

```java
package com.saascon.core.audit;

import com.saascon.core.tenant.TenantContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuditService {

    private final AuditLogRepository auditLogRepository;

    // ❌ ORIGINAL (roto):
    // private final TenantContext tenantContext; ← NoSuchBeanDefinitionException

    public void log(String entityType, String entityId, String operation) {

        // ✅ CORRECTO: llamada estática — TenantContext no es un bean
        String tenantId = TenantContext.get();

        // ✅ CORRECTO: extraer userId del SecurityContext directamente
        String userId = getCurrentUserId();

        AuditLogEntity entry = AuditLogEntity.builder()
                .tenantId(tenantId)
                .userId(userId)
                .entityType(entityType)
                .entityId(entityId)
                .operation(operation)
                .timestamp(java.time.Instant.now())
                .build();

        auditLogRepository.save(entry);
        log.info("Audit: tenant={} user={} {}:{} op={}", tenantId, userId, entityType, entityId, operation);
    }

    private String getCurrentUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return (auth != null && auth.getName() != null) ? auth.getName() : "system";
    }
}
```

### AuditLogEntity

```java
@Entity
@Table(name = "audit_log", schema = "public") // siempre en schema público
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuditLogEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false) private String tenantId;
    @Column                   private String userId;
    @Column(nullable = false) private String entityType;
    @Column(nullable = false) private String entityId;
    @Column(nullable = false) private String operation; // CREATE, UPDATE, DELETE
    @Column(nullable = false) private java.time.Instant timestamp;

    @Column(columnDefinition = "jsonb")
    private String details; // JSON con valores anteriores/nuevos
}
```

### Uso en servicios
```java
@Service
@RequiredArgsConstructor
@Transactional
public class CreateTenantUseCase {

    private final TenantRepository tenantRepository;
    private final TenantMapper tenantMapper;
    private final AuditService auditService;

    public TenantResponse execute(CreateTenantRequest request) {
        TenantEntity saved = tenantRepository.save(tenantMapper.toEntity(request));
        auditService.log("tenant", saved.getId().toString(), "CREATE");
        return tenantMapper.toResponse(saved);
    }
}
```

---

## 4. JWT Configuration

```java
package com.saascon.core.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtTokenProvider {

    private static final long ACCESS_MS  = 3_600_000L;   // 1 hora
    private static final long REFRESH_MS = 604_800_000L; // 7 días

    @Value("${jwt.secret}")
    private String secret;

    public String generateAccessToken(String userId, String tenantId) {
        return Jwts.builder()
                .subject(userId)
                .claim("tenant_id", tenantId) // REQUERIDO — lo lee TenantFilter
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + ACCESS_MS))
                .signWith(signingKey())
                .compact();
    }

    public String generateRefreshToken(String userId, String tenantId) {
        return Jwts.builder()
                .subject(userId)
                .claim("tenant_id", tenantId)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + REFRESH_MS))
                .signWith(signingKey())
                .compact();
    }

    private SecretKey signingKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }
}
```

---

## 5. Rate Limiting — Bucket4j

# ════════════════════════════════════════════════════════════════
# ✅ CORRECCIÓN #2 — @RateLimiter con anotación custom no existe en Spring
#
# ORIGINAL:
#   @RateLimiter(name = "login", max = 5, timeout = 60)
#   Esta anotación NO existe en Spring Boot por defecto.
#   Requeriría una librería custom o Resilience4j configurado específicamente.
#
# CORRECCIÓN:
#   Usar Bucket4j, que es la librería estándar para rate limiting en Java.
#   Dependencia: com.bucket4j:bucket4j-core
# ════════════════════════════════════════════════════════════════

```xml
<!-- pom.xml -->
<dependency>
    <groupId>com.bucket4j</groupId>
    <artifactId>bucket4j-core</artifactId>
    <version>8.10.1</version>
</dependency>
```

```java
@Configuration
public class RateLimitConfig {

    @Bean
    public Bucket loginBucket() {
        // 5 intentos por minuto
        Bandwidth limit = Bandwidth.classic(5, Refill.greedy(5, Duration.ofMinutes(1)));
        return Bucket.builder().addLimit(limit).build();
    }
}

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final LoginUseCase loginUseCase;
    private final Bucket loginBucket; // inyectado desde RateLimitConfig

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {

        // ✅ CORRECTO: rate limiting real con Bucket4j
        // ❌ ORIGINAL: @RateLimiter(name="login") no existe en Spring por defecto
        if (!loginBucket.tryConsume(1)) {
            return ResponseEntity.status(429)
                    .header("Retry-After", "60")
                    .build();
        }

        return ResponseEntity.ok(loginUseCase.execute(request));
    }
}
```

---

## 6. Password Encoding

```java
@Bean
public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder(12); // work factor 12 mínimo
}
```

---

## Security Checklist

- [ ] MapStruct para TODOS los mappings entity ↔ DTO
- [ ] `@Valid` en TODOS los `@RequestBody`
- [ ] Bean Validation en TODOS los request DTOs
- [ ] `AuditService.log()` en todas las operaciones CREATE, UPDATE, DELETE
- [ ] `TenantContext.get()` como llamada estática (no inyectado como bean)
- [ ] BCryptPasswordEncoder con work factor ≥ 12
- [ ] JWT contiene claim `tenant_id`
- [ ] Rate limiting en `/auth/login` con Bucket4j
- [ ] Nunca exponer `@Entity` en responses
- [ ] `shouldNotFilter()` en TenantFilter para rutas `/auth` y `/admin`