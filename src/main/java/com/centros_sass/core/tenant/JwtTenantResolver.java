package com.centros_sass.core.tenant;

import com.centros_sass.core.exception.TenantResolutionException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

/**
 * JwtTenantResolver — extrae el tenant_id del JWT del usuario autenticado.
 *
 * ¿Por qué existe esta clase?
 * Cuando un usuario hace un request, Spring Security ya validó su JWT y guardó
 * la autenticación en el SecurityContext. Esta clase lee ese JWT ya validado
 * y extrae el claim "tenant_id" que indica a qué tenant pertenece el usuario.
 *
 * ¿Por qué JwtAuthenticationToken y no Authentication directamente?
 * Authentication es una interfaz genérica de Spring Security — no tiene método getToken().
 * JwtAuthenticationToken es la implementación concreta que usa Spring OAuth2 Resource Server
 * para representar un usuario autenticado con JWT. Solo ella tiene getToken() que devuelve
 * el objeto Jwt con los claims.
 *
 * Flujo:
 *   SecurityContext → Authentication → (cast) JwtAuthenticationToken → getToken() → getClaimAsString("tenant_id")
 *
 * Esta clase SÍ es un Spring Bean (@Component) porque necesita ser inyectada en TenantFilter.
 */
@Component
public class JwtTenantResolver {

    /**
     * Lee el SecurityContext y extrae el claim "tenant_id" del JWT.
     *
     * @return el tenant_id del usuario autenticado (ej: "acme", "globex")
     * @throws TenantResolutionException si no hay JWT, o si el JWT no tiene claim tenant_id
     */
    public String resolveTenantId() {
        // Obtener la autenticación del hilo actual — Spring la guardó aquí después de validar el JWT
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        // Pattern matching con instanceof (Java 16+): verifica el tipo Y hace el cast en una sola línea.
        // Si auth es null o no es JwtAuthenticationToken, el bloque no se ejecuta.
        // JwtAuthenticationToken SÍ tiene getToken() → objeto Jwt con todos los claims del token.
        if (auth instanceof JwtAuthenticationToken jwtAuth) {
            String tenantId = jwtAuth.getToken().getClaimAsString("tenant_id");

            // Verificar que el claim existe y no está vacío
            // isBlank() cubre tanto null como strings de solo espacios
            if (tenantId != null && !tenantId.isBlank()) {
                return tenantId;
            }
        }

        // Si llegamos aquí: no había JWT, o el JWT no tenía el claim tenant_id.
        // Lanzar excepción específica — TenantFilter la capturará y decidirá qué hacer.
        throw new TenantResolutionException("No tenant_id claim found in JWT");
    }
}

// ─── ¿QUÉ APRENDER DE ESTA CLASE? ────────────────────────────
// 1. SecurityContextHolder: dónde Spring guarda la autenticación del request actual
// 2. JwtAuthenticationToken vs Authentication: siempre usar la implementación concreta
//    cuando necesitas acceder a métodos específicos (getToken())
// 3. Pattern matching con instanceof (Java 16+): if (auth instanceof JwtAuthenticationToken jwtAuth)
//    — más limpio que el cast explícito: (JwtAuthenticationToken) auth
// 4. Esta clase necesita spring-boot-starter-oauth2-resource-server en el pom.xml
//    porque JwtAuthenticationToken viene de ese módulo, no del starter-security base
// ──────────────────────────────────────────────────────────────
