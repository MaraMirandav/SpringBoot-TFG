# Skill: Architecture — Spring Modulith + Hexagonal

# ════════════════════════════════════════════════════════════════
# ✅ CORRECCIÓN #1 — Módulos renombrados para coincidir con el SaaS real
# El original tenía: auth / core / billing
# Estos no representan la arquitectura de tres capas de un SaaS B2B.
# Claude habría generado paquetes equivocados desde el primer commit.
# ════════════════════════════════════════════════════════════════

## Module Layout
```
com.saascon.
├── core/       # Shared: TenantContext, Security, BaseEntity, exceptions
├── admin/      # Admin panel: tenant CRUD, plan management, global metrics
├── customer/   # Customer portal: registration, billing, account management
└── app/        # SaaS product: tenant-scoped business logic
```

## Hexagonal Structure (inside each module)
```
{module}/
├── domain/
│   ├── entity/         # JPA entities — aggregate roots
│   ├── valueobject/    # Immutable value objects (Email, Money, TenantSlug)
│   ├── event/          # Domain events (TenantProvisionedEvent, etc.)
│   └── repository/     # Repository interfaces (output ports)
├── application/
│   ├── service/        # Use cases — one class per use case
│   ├── dto/            # Request/Response records (never entities)
│   └── mapper/         # MapStruct mappers
├── port/
│   ├── in/             # Input port interfaces (what controllers call)
│   └── out/            # Output port interfaces (what services call)
└── adapter/
    ├── in/
    │   └── controller/ # REST controllers — HTTP concerns only
    └── out/
        └── repository/ # JPA repository implementations
```

## Dependency Rules
```
adapter (in/out)
    │ depends on
    ▼
application (use cases)
    │ depends on
    ▼
domain (entities, events, repository interfaces)
    │ depends on
    ▼
  nothing
```

**Key principle**: `domain` has ZERO external dependencies.
No Spring, no JPA imports in domain — only plain Java.

## Correct Pattern — Controller to Use Case

```java
// ✅ CORRECT: controller only handles HTTP
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/tenants")
public class TenantController {

    private final CreateTenantUseCase createTenantUseCase;

    @PostMapping
    public ResponseEntity<TenantResponse> create(
            @Valid @RequestBody CreateTenantRequest request) {
        TenantResponse response = createTenantUseCase.execute(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}

// ✅ CORRECT: use case contains all business logic
@Service
@RequiredArgsConstructor
@Transactional
public class CreateTenantUseCase {

    private final TenantRepository tenantRepository;
    private final TenantMapper tenantMapper;
    private final ApplicationEventPublisher eventPublisher;

    public TenantResponse execute(CreateTenantRequest request) {
        TenantEntity tenant = tenantMapper.toEntity(request);
        TenantEntity saved = tenantRepository.save(tenant);
        eventPublisher.publishEvent(new TenantProvisionedEvent(saved.getId(), saved.getSlug()));
        return tenantMapper.toResponse(saved);
    }
}
```

```java
// ❌ FORBIDDEN: controller accessing repository directly
@RestController
public class TenantController {
    @Autowired
    private TenantRepository tenantRepository; // NEVER
}
```

## Module Communication — Events Only

Modules NEVER call each other directly. Use `ApplicationEvent`:

```java
// Domain event (in core or admin domain/event/)
public record TenantProvisionedEvent(Long tenantId, String slug) {}

// Publisher — admin module
@Service
@RequiredArgsConstructor
public class CreateTenantUseCase {
    private final ApplicationEventPublisher eventPublisher;

    public TenantResponse execute(CreateTenantRequest request) {
        // ... save tenant
        eventPublisher.publishEvent(new TenantProvisionedEvent(tenant.getId(), tenant.getSlug()));
        return tenantMapper.toResponse(tenant);
    }
}

// Listener — app module (reacts to tenant creation)
@Service
@Slf4j
public class TenantSchemaInitializer {

    @EventListener
    @Transactional
    public void onTenantProvisioned(TenantProvisionedEvent event) {
        log.info("Initializing schema for tenant: {}", event.slug());
        // run Flyway migrations for tenant_{slug}
    }
}
```

# ════════════════════════════════════════════════════════════════
# ✅ CORRECCIÓN #2 — Tabla de naming conventions actualizada
# El original tenía sufijos inconsistentes con el stack SaaS.
# Se agregó TenantSlug como ejemplo de value object real del proyecto.
# ════════════════════════════════════════════════════════════════

## Naming Conventions

| Layer              | Pattern                      | Example                     |
|--------------------|------------------------------|-----------------------------|
| Entity             | `{Name}Entity`               | `TenantEntity`              |
| Value Object       | noun (no suffix)             | `Email`, `TenantSlug`       |
| Domain Event       | `{Fact}Event`                | `TenantProvisionedEvent`    |
| Repository (port)  | `{Name}Repository`           | `TenantRepository`          |
| Use Case           | `{Action}{Name}UseCase`      | `CreateTenantUseCase`       |
| Controller         | `{Name}Controller`           | `TenantController`          |
| Request DTO        | `{Action}{Name}Request`      | `CreateTenantRequest`       |
| Response DTO       | `{Name}Response`             | `TenantResponse`            |
| Mapper             | `{Name}Mapper`               | `TenantMapper`              |
| Event Listener     | `{Name}EventListener`        | `TenantSchemaInitializer`   |

## Testing Strategy

### Unit Tests — domain logic, no Spring context
```java
@ExtendWith(MockitoExtension.class)
class CreateTenantUseCaseTest {

    @Mock TenantRepository tenantRepository;
    @Mock TenantMapper tenantMapper;
    @Mock ApplicationEventPublisher eventPublisher;
    @InjectMocks CreateTenantUseCase useCase;

    @Test
    @DisplayName("should create tenant and publish provisioning event")
    void shouldCreateTenantAndPublishEvent() {
        // Arrange
        CreateTenantRequest request = new CreateTenantRequest("Acme Corp", "acme", "admin@acme.com");
        TenantEntity entity = new TenantEntity();
        entity.setId(1L);
        when(tenantMapper.toEntity(request)).thenReturn(entity);
        when(tenantRepository.save(entity)).thenReturn(entity);

        // Act
        useCase.execute(request);

        // Assert
        verify(tenantRepository).save(entity);
        verify(eventPublisher).publishEvent(any(TenantProvisionedEvent.class));
    }
}
```

### Integration Tests — Testcontainers with real PostgreSQL
```java
@SpringBootTest
@Testcontainers
class TenantRepositoryIT {

    @Container
    static PostgreSQLContainer<?> postgres =
            new PostgreSQLContainer<>("postgres:16-alpine");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }
}
```

### Controller Tests — @WebMvcTest, mocked use cases
```java
@WebMvcTest(TenantController.class)
class TenantControllerTest {

    @Autowired MockMvc mockMvc;
    @MockBean CreateTenantUseCase createTenantUseCase;

    @Test
    @DisplayName("POST /api/v1/admin/tenants should return 201")
    void shouldReturn201OnCreate() throws Exception {
        mockMvc.perform(post("/api/v1/admin/tenants")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {"name":"Acme","slug":"acme","adminEmail":"admin@acme.com"}
                        """))
                .andExpect(status().isCreated());
    }
}
```