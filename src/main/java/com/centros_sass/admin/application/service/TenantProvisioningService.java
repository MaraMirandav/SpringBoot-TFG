package com.centros_sass.admin.application.service;

import com.centros_sass.admin.domain.model.TenantEntity;
import com.centros_sass.admin.domain.model.TenantStatus;
import com.centros_sass.admin.domain.repository.TenantRepository;
import com.centros_sass.core.tenant.TenantContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.flywaydb.core.Flyway;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

@Service
@RequiredArgsConstructor
@Slf4j
public class TenantProvisioningService {

    private final TenantRepository tenantRepository;
    private final DataSource dataSource;
    private final TenantSeedService tenantSeedService;

    @Transactional
    public TenantEntity provisionTenant(
            String name, String slug, String adminEmail, String planSlug,
            String directorEmail, String directorPassword, String directorFirstName,
            String directorFirstSurname, String directorDni, String directorPhone) {

        log.info("PROVISIONING: Iniciando proceso para '{}'", slug);

        // 1. Guardar registro en PUBLIC
        TenantEntity tenant = TenantEntity.builder()
                .name(name).slug(slug).adminEmail(adminEmail)
                .status(TenantStatus.ACTIVE).build();
        tenant = tenantRepository.save(tenant);
        log.info("PROVISIONING: 1. Registro creado en schema public");

        try {
            // 2. Crear Schema Físico
            createSchema(slug);
            log.info("PROVISIONING: 2. Schema físico creado");

            // 3. Ejecutar Flyway
            migrateSchema(slug);
            log.info("PROVISIONING: 3. Migraciones Flyway completadas");

            // 4. Seeding (IMPORTANTE: El contexto se pone AQUÍ)
            log.info("PROVISIONING: 4. Preparando Seeding...");
            TenantContext.set(slug);
            try {
                tenantSeedService.seedDirector(
                        slug, directorEmail, directorPassword, directorFirstName,
                        directorFirstSurname, directorDni, directorPhone);
            } finally {
                // Siempre limpiar el contexto
                TenantContext.clear();
                log.info("PROVISIONING: Contexto limpiado.");
            }

        } catch (Exception e) {
            log.error("PROVISIONING ERROR: Abortando y limpiando... {}", e.getMessage());
            cleanupSchema(slug);
            tenantRepository.delete(tenant);
            throw new RuntimeException("Error en la provisión del tenant", e);
        }

        log.info("PROVISIONING: ¡Todo finalizado con éxito para {}!", slug);
        return tenant;
    }

    private void createSchema(String slug) {
        String schemaName = "tenant_" + slug.replace("-", "_");
        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute("CREATE SCHEMA IF NOT EXISTS " + schemaName);
        } catch (SQLException e) {
            throw new RuntimeException("Error al crear el schema " + schemaName, e);
        }
    }

    private void migrateSchema(String slug) {
        String schemaName = "tenant_" + slug.replace("-", "_");
        Flyway flyway = Flyway.configure()
                .dataSource(dataSource)
                .schemas(schemaName)
                .locations("classpath:bbdd/migration/tenant")
                .load();
        flyway.migrate();
    }

    private void cleanupSchema(String slug) {
        String schemaName = "tenant_" + slug.replace("-", "_");
        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute("DROP SCHEMA IF EXISTS " + schemaName + " CASCADE");
        } catch (SQLException e) {
            log.error("Error al limpiar schema {}", schemaName);
        }
    }
}