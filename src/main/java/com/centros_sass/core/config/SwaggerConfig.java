package com.centros_sass.core.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * SwaggerConfig — configuración de Swagger/OpenAPI para la UI de documentación.
 *
 * Añade el botón "Authorize" en Swagger UI para que los usuarios
 * puedan introducir su token JWT directamente en la UI y probar los endpoints.
 *
 * El token se envía en el header "Authorization: Bearer <token>" automáticamente.
 */
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("SaasCon API")
                .description("Plataforma SaaS multitenant para Centros de Día")
                .version("1.0.0"))
            .addSecurityItem(new SecurityRequirement()
                .addList("Bearer Authentication"))
            .components(new Components()
                .addSecuritySchemes("Bearer Authentication",
                    new SecurityScheme()
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("JWT")
                        .description("Introduce el token JWT obtenido del login")));
    }
}