package com.centros_sass.core.tenant;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

@Slf4j
@Component
@RequiredArgsConstructor
public class MultiTenantConnectionProviderImpl implements MultiTenantConnectionProvider<String> {

    private final DataSource dataSource;

    @Override
    public Connection getAnyConnection() throws SQLException {
        return dataSource.getConnection();
    }

    @Override
    public void releaseAnyConnection(Connection connection) throws SQLException {
        connection.close();
    }

    @Override
    public Connection getConnection(String tenantId) throws SQLException {
        log.debug("DEBUG-DB: Hibernate solicita conexión para tenantId: '{}'", tenantId);
        
        final Connection connection = getAnyConnection();
        try (Statement stmt = connection.createStatement()) {
            if (tenantId != null && !tenantId.equals("public")) {
                // Transformación: centro-madrid -> tenant_centro_madrid
                String schemaName = "tenant_" + tenantId.replace("-", "_");

                if (!schemaName.matches("^tenant_[a-z0-9_]+$")) {
                    connection.close();
                    throw new IllegalArgumentException("TenantId inválido: " + tenantId);
                }
                
                log.debug("DEBUG-DB: Ejecutando SET search_path TO {}, public", schemaName);
                stmt.execute("SET search_path TO " + schemaName + ", public");
            } else {
                log.debug("DEBUG-DB: Usando schema public");
                stmt.execute("SET search_path TO public");
            }
        } catch (SQLException e) {
            log.error("DEBUG-DB: Error al establecer el search_path para tenant {}", tenantId);
            connection.close();
            throw e;
        }
        return connection;
    }

    @Override
    public void releaseConnection(String tenantId, Connection connection) throws SQLException {
        log.debug("DEBUG-DB: Liberando conexión de '{}'. Reseteando a public.", tenantId);
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("SET search_path TO public");
        } catch (SQLException e) {
            log.error("DEBUG-DB: Error al resetear search_path");
        }
        connection.close();
    }

    @Override
    public boolean supportsAggressiveRelease() { return false; }
    @Override
    public boolean isUnwrappableAs(Class<?> unwrapType) { return false; }
    @Override
    public <T> T unwrap(Class<T> unwrapType) { return null; }
}