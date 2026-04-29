package com.centros_sass.core.config;

import javax.sql.DataSource;
import org.flywaydb.core.Flyway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * FlywayConfig — configuración explícita de Flyway para garantizar migraciones.
 *
 * PROBLEMA: Flyway no se está ejecutando automáticamente.
 * SOLUCIÓN: Configuremos Flyway programáticamente.
 */
@Configuration
public class FlywayConfig {

    @Bean
    @Primary
    public Flyway flyway(DataSource dataSource) {
        Flyway flyway = Flyway.configure()
                .dataSource(dataSource)
                .locations("classpath:bbdd/migration/public")
                .schemas("public")
                .baselineOnMigrate(true)
                .table("flyway_schema_history")
                .load();
        
        flyway.migrate();
        
        return flyway;
    }
}