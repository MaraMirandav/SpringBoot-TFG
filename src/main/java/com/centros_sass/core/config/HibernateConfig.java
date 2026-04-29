package com.centros_sass.core.config;

import lombok.RequiredArgsConstructor;
import org.hibernate.cfg.Environment;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import com.centros_sass.core.tenant.MultiTenantConnectionProviderImpl;
import com.centros_sass.core.tenant.TenantIdentifierResolver;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * HibernateConfig — configura Hibernate con soporte de multitenancy schema-per-tenant.
 *
 * ¿Por qué existe esta clase?
 * Spring Boot configura Hibernate automáticamente en modo single-tenant.
 * Para multitenancy, necesitamos anular esa configuración y registrar manualmente:
 *   1. MultiTenantConnectionProviderImpl → gestiona conexiones por tenant
 *   2. TenantIdentifierResolver          → le dice a Hibernate qué tenant usar
 *
 * Sin esta clase, Hibernate no sabría que debe operar en modo multitenant.
 * application.properties no tiene propiedades suficientes para configurar esto —
 * los dos beans de multitenancy solo pueden registrarse programáticamente.
 *
 * ¿Qué es LocalContainerEntityManagerFactoryBean?
 * Es la forma estándar de Spring para crear el EntityManagerFactory de JPA.
 * EntityManagerFactory es la "fábrica" de sesiones Hibernate — cada request
 * obtiene un EntityManager de aquí para hacer operaciones en la base de datos.
 *
 * Nota: este bean reemplaza la auto-configuración de Spring Boot para JPA.
 * Por eso debemos configurar manualmente el VendorAdapter y el paquete de escaneo.
 */
@Configuration
@RequiredArgsConstructor
public class HibernateConfig {

    // Inyectados por constructor via @RequiredArgsConstructor
    // Son los dos componentes que Hibernate necesita para multitenancy
    private final MultiTenantConnectionProviderImpl connectionProvider;
    private final TenantIdentifierResolver tenantIdentifierResolver;

    /**
     * Crea y configura el EntityManagerFactory con soporte multitenant.
     *
     * Spring Boot normalmente crea este bean automáticamente, pero al definirlo
     * nosotros aquí, tomamos el control total de la configuración de Hibernate.
     *
     * @param dataSource el DataSource (pool de conexiones HikariCP) inyectado por Spring Boot
     * @return el EntityManagerFactory configurado para multitenancy schema-per-tenant
     */
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {

        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();

        // Usar el mismo DataSource que Spring Boot configuró (HikariCP con las propiedades del application.properties)
        em.setDataSource(dataSource);

        // HibernateJpaVendorAdapter adapta Spring JPA para usar Hibernate como proveedor
        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());

        // Escanear TODOS los paquetes de la app para encontrar clases @Entity
        // "com.centros_sass" cubre core, admin, customer y app — todos los módulos
        em.setPackagesToScan("com.centros_sass");

        // Propiedades adicionales de Hibernate para activar multitenancy
        Map<String, Object> props = new HashMap<>();

        // Desactivar DDL automático — solo Flyway maneja esquemas
        // "validate" verifica que tablas existan, no las crea
        props.put(Environment.HBM2DDL_AUTO, "none");

        // Registrar el proveedor de conexiones multitenant — le dice a Hibernate cómo
        // obtener y liberar conexiones por tenant (implementado en MultiTenantConnectionProviderImpl)
        props.put(Environment.MULTI_TENANT_CONNECTION_PROVIDER, connectionProvider);

        // Registrar el resolvedor de tenant — le dice a Hibernate qué tenant usar
        // en cada momento (implementado en TenantIdentifierResolver via TenantContext)
        props.put(Environment.MULTI_TENANT_IDENTIFIER_RESOLVER, tenantIdentifierResolver);

        // Dialecto de PostgreSQL — optimizaciones específicas de PG (tipos, funciones, etc.)
        props.put(Environment.DIALECT, "org.hibernate.dialect.PostgreSQLDialect");

        em.setJpaPropertyMap(props);

        return em;
    }
}

// ─── ¿QUÉ APRENDER DE ESTA CLASE? ────────────────────────────
// 1. @Configuration: clase de configuración de Spring — los @Bean que define reemplazan
//    la auto-configuración de Spring Boot cuando hay conflicto de tipo
// 2. LocalContainerEntityManagerFactoryBean: la forma Spring de crear EntityManagerFactory
//    — es el punto de entrada de JPA/Hibernate en Spring
// 3. Environment.MULTI_TENANT_*: constantes de Hibernate para registrar los dos componentes
//    de multitenancy — sin estas dos líneas, Hibernate opera en modo single-tenant
// 4. setPackagesToScan("com.centros_sass"): escanea todos los módulos — si solo pones "com.centros_sass.core"
//    las entidades de admin, customer y app no serían detectadas
// 5. Este bean "toma el control" de JPA: Spring Boot ve que ya existe un EntityManagerFactory
//    definido y no crea el suyo — por eso debemos configurar todo manualmente aquí
// ──────────────────────────────────────────────────────────────
