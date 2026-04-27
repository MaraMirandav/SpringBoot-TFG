# Skill: Multitenancy — Schema-per-Tenant

## Flow
```
HTTP Request
    → JwtAuthFilter        (valida y parsea el JWT)
    → TenantFilter         (extrae tenant_id, puebla TenantContext)
    → Controller / Service
    → Hibernate            (lee TenantContext, llama ConnectionProvider)
    → SET search_path TO tenant_{slug}, public
    → PostgreSQL
```

---

## 1. TenantContext — ThreadLocal holder

Sin cambios respecto al original. Es correcto.

```java
package com.saascon.core.tenant;

// Clase final con constructor privado — no es un Spring bean.
// Se usa con llamadas estáticas: TenantContext.set(), TenantContext.get()
public final class TenantContext {

    private static final ThreadLocal<String> CURRENT = new ThreadLocal<>();

    private TenantContext() {} // utility class — no instances

    public static void set(String tenantId) {
        CURRENT.set(tenantId);
    }

    public static String get() {
        return CURRENT.get();
    }

    public static void clear() {
        CURRENT.remove(); // SIEMPRE limpiar en el finally del filtro
    }
}
```

---

## 2. JwtTenantResolver

# ════════════════════════════════════════════════════════════════
# ✅ CORRECCIÓN #1 — API de Spring Security incorrecta
#
# ORIGINAL (roto):
#   auth.getToken()  →  Authentication NO tiene método getToken()
#   Esta línea no compilaría. Authentication es una interfaz genérica.
#
# CORRECCIÓN:
#   Usar JwtAuthenticationToken, que es la implementación concreta
#   de Spring Security OAuth2 Resource Server para JWT.
#   JwtAuthenticationToken SÍ tiene getToken() que devuelve un Jwt.
# ════════════════════════════════════════════════════════════════

```java
package com.saascon.core.tenant;

import com.saascon.core.exception.TenantResolutionException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken; // ← CORRECTO
import org.springframework.stereotype.Component;

@Component
public class JwtTenantResolver {

    public String resolveTenantId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        // ✅ CORRECTO: cast a JwtAuthenticationToken, que SÍ tiene getToken()
        // ❌ ORIGINAL: auth.getToken() — Authentication no tiene ese método
        if (auth instanceof JwtAuthenticationToken jwtAuth) {
            String tenantId = jwtAuth.getToken().getClaimAsString("tenant_id");
            if (tenantId != null && !tenantId.isBlank()) {
                return tenantId;
            }
        }

        throw new TenantResolutionException("No tenant_id claim found in JWT");
    }
}
```

---

## 3. TenantFilter

# ════════════════════════════════════════════════════════════════
# ✅ CORRECCIÓN #2 — shouldNotFilter agregado
#
# ORIGINAL: no tenía shouldNotFilter().
# El filtro intentaba extraer tenant_id incluso en endpoints públicos
# como /auth/login (donde no hay JWT todavía) → NullPointerException.
#
# CORRECCIÓN: excluir rutas /admin y /auth del filtro de tenant.
# ════════════════════════════════════════════════════════════════

```java
package com.saascon.core.tenant;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Order(2) // ejecutar DESPUÉS del JwtAuthFilter (@Order(1))
public class TenantFilter extends OncePerRequestFilter {

    private final JwtTenantResolver tenantResolver;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {
        try {
            String tenantId = tenantResolver.resolveTenantId();
            TenantContext.set(tenantId);
            chain.doFilter(request, response);
        } catch (TenantResolutionException e) {
            // Rutas que pasan shouldNotFilter() nunca llegan aquí,
            // pero si alguna ruta pública llega y falla, continúa sin tenant.
            chain.doFilter(request, response);
        } finally {
            TenantContext.clear(); // SIEMPRE limpiar — evita memory leaks entre requests
        }
    }

    // ✅ CORRECCIÓN #2: rutas que NO necesitan tenant context
    // ❌ ORIGINAL: no existía este método → crash en login y endpoints admin
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        return path.startsWith("/api/v1/auth")   // login, refresh token
            || path.startsWith("/api/v1/admin")  // admin no usa tenant context
            || path.startsWith("/actuator");      // health checks
    }
}
```

---

## 4. MultiTenantConnectionProvider

# ════════════════════════════════════════════════════════════════
# ✅ CORRECCIÓN #3 — SQLException no declarado en firmas de métodos
#
# ORIGINAL (roto):
#   public Connection getConnection(String tenantIdentifier) {
#   public void releaseConnection(String tenantIdentifier, Connection connection) {
#   → La interfaz MultiTenantConnectionProvider OBLIGA a declarar
#     "throws SQLException". Sin eso, no compila.
#
# CORRECCIÓN:
#   Agregar "throws SQLException" a todos los métodos que lo requieren.
#   También: generic tipado como <String> en la interfaz (Hibernate 6+).
# ════════════════════════════════════════════════════════════════

```java
package com.saascon.core.tenant;

import lombok.RequiredArgsConstructor;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;

@Component
@RequiredArgsConstructor
public class MultiTenantConnectionProviderImpl
        implements MultiTenantConnectionProvider<String>, Serializable { // ← <String> requerido en Hibernate 6

    private final DataSource dataSource;

    // ✅ CORRECTO: "throws SQLException" declarado
    // ❌ ORIGINAL: faltaba "throws SQLException" → error de compilación
    @Override
    public Connection getAnyConnection() throws SQLException {
        return dataSource.getConnection();
    }

    // ✅ CORRECTO: "throws SQLException" declarado
    // ❌ ORIGINAL: faltaba "throws SQLException" → no compila
    @Override
    public Connection getConnection(String tenantId) throws SQLException {
        Connection connection = dataSource.getConnection();
        String schema = (tenantId != null && !tenantId.isBlank())
                ? "tenant_" + tenantId
                : "public";
        // SET search_path hace que Hibernate use el schema del tenant
        connection.createStatement().execute("SET search_path TO " + schema + ", public");
        return connection;
    }

    // ✅ CORRECTO: "throws SQLException" declarado
    // ❌ ORIGINAL: faltaba "throws SQLException" → no compila
    @Override
    public void releaseAnyConnection(Connection connection) throws SQLException {
        connection.close();
    }

    // ✅ CORRECTO: "throws SQLException" declarado + reset del search_path
    // ❌ ORIGINAL: faltaba "throws SQLException" y no reseteaba el schema
    //    (si el pool reutiliza la conexión, el próximo request heredaría
    //     el search_path del tenant anterior — fuga de datos entre tenants)
    @Override
    public void releaseConnection(String tenantId, Connection connection) throws SQLException {
        connection.createStatement().execute("SET search_path TO public"); // reset
        connection.close();
    }

    @Override
    public boolean supportsAggressiveRelease() {
        return false;
    }

    @Override
    public boolean isUnwrappableAs(Class<?> unwrapType) {
        return false;
    }

    @Override
    public <T> T unwrap(Class<T> unwrapType) {
        return null;
    }
}
```

---

## 5. CurrentTenantIdentifierResolver

```java
package com.saascon.core.tenant;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.stereotype.Component;

@Component
public class TenantIdentifierResolver
        implements CurrentTenantIdentifierResolver<String> { // ← <String> requerido en Hibernate 6

    @Override
    public String resolveCurrentTenantIdentifier() {
        String tenant = TenantContext.get(); // llamada estática — correcto
        return (tenant != null) ? tenant : "public"; // fallback al schema global
    }

    @Override
    public boolean validateExistingCurrentSessions() {
        return true;
    }
}
```

---

## 6. HibernateConfig

```java
package com.saascon.core.config;

import com.saascon.core.tenant.MultiTenantConnectionProviderImpl;
import com.saascon.core.tenant.TenantIdentifierResolver;
import lombok.RequiredArgsConstructor;
import org.hibernate.cfg.Environment;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class HibernateConfig {

    private final MultiTenantConnectionProviderImpl connectionProvider;
    private final TenantIdentifierResolver tenantIdentifierResolver;

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {

        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        em.setPackagesToScan("com.saascon"); // escanea todos los módulos

        Map<String, Object> props = new HashMap<>();
        props.put(Environment.MULTI_TENANT_CONNECTION_PROVIDER, connectionProvider);
        props.put(Environment.MULTI_TENANT_IDENTIFIER_RESOLVER, tenantIdentifierResolver);
        props.put(Environment.DIALECT, "org.hibernate.dialect.PostgreSQLDialect");
        em.setJpaPropertyMap(props);

        return em;
    }
}
```

---

## 7. Flyway — Migrations per tenant

```
src/main/resources/db/migration/
├── public/            # Datos globales (admin)
│   ├── V1__create_tenants_table.sql
│   ├── V2__create_plans_table.sql
│   └── V3__create_audit_log_table.sql
└── tenant/            # Template por tenant
    └── V1__init_tenant_schema.sql   # users, roles, permisos del tenant
```

### Crear schema al provisionar un tenant
```java
@Service
@RequiredArgsConstructor
@Slf4j
public class TenantProvisioningService {

    private final DataSource dataSource;

    @Transactional
    public void provisionSchema(String slug) {
        log.info("Provisioning schema for tenant: {}", slug);

        Flyway.configure()
                .dataSource(dataSource)
                .schemas("tenant_" + slug)
                .locations("classpath:db/migration/tenant")
                .load()
                .migrate();

        log.info("Schema tenant_{} ready", slug);
    }
}
```

---

## Schema Strategy

| Data                            | Schema             | Example tables                  |
|---------------------------------|--------------------|---------------------------------|
| Global / catalog                | `public`           | tenants, plans, audit_log       |
| Tenant-specific                 | `tenant_{slug}`    | users, orders, invoices         |

---

## Important Rules

1. `TenantContext.clear()` SIEMPRE en el `finally` del filtro
2. Resetear `search_path` en `releaseConnection()` antes de cerrar
3. JWT DEBE contener claim `tenant_id`
4. `@Transactional` requerido en cualquier operación que use Hibernate con tenant
5. Rutas `/auth` y `/admin` deben estar en `shouldNotFilter()`