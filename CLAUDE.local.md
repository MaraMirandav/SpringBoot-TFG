# Mis preferencias personales de aprendizaje

## Comentarios en el código — OBLIGATORIO
Soy estudiante de grado aprendiendo Spring Boot y arquitectura SaaS.
Todo el código que generes DEBE incluir comentarios detallados que expliquen:

- Qué hace cada clase y por qué existe
- Qué hace cada método, qué recibe y qué devuelve
- Qué hace cada línea o bloque importante
- Por qué se tomó esa decisión de diseño (no solo el "qué" sino el "por qué")

### Formato de comentarios esperado

Para clases:
/**
 * TenantContext — almacena el ID del tenant actual para este hilo de ejecución.
 *
 * En una app multitenant, múltiples usuarios hacen requests al mismo tiempo.
 * Cada request corre en su propio "hilo" (thread). ThreadLocal nos permite
 * guardar un valor por hilo sin que se mezcle con los demás hilos.
 *
 * Ejemplo: usuario de tenant "acme" y usuario de tenant "globex" pueden
 * hacer requests simultáneos y cada uno verá solo sus datos.
 */

Para métodos:
/**
 * Establece el tenant activo para el hilo actual.
 *
 * @param tenantId el identificador único del tenant (ej: "acme", "globex")
 *                 Este valor viene del JWT token del usuario autenticado.
 */

Para líneas clave:
// ThreadLocal garantiza que cada hilo tenga su propia copia de este valor
// Sin esto, los tenants se mezclarían y usuario A vería datos de usuario B
private static final ThreadLocal<String> CURRENT = new ThreadLocal<>();

## Idioma
- Comentarios: español (para que los entienda mejor)
- Código (variables, métodos, clases): inglés (convención universal)
- Explicaciones adicionales: español

## Cuando termines una clase
Al final de cada clase generada, agrega un bloque así:

// ─── ¿QUÉ APRENDER DE ESTA CLASE? ────────────────────────────
// 1. ThreadLocal: permite datos por-hilo sin sincronización
// 2. Patrón Utility Class: constructor privado = no se instancia
// 3. Por qué static: TenantContext no necesita ser un Spring Bean
// ──────────────────────────────────────────────────────────────