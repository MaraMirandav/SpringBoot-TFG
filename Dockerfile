# Etapa 1: Construcción
FROM maven:3.9-eclipse-temurin-21 AS builder
WORKDIR /app
# Se copia el pom.xml primero para aprovechar la caché de Docker
COPY pom.xml .
RUN mvn dependency:go-offline
# Se copia el código fuente y se compila el proyecto
COPY src ./src
RUN mvn clean package -DskipTests

# Etapa 2: Ejecución (Contenedor final súper ligero)
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
# Se copia únicamente el .jar compilado de la etapa anterior
COPY --from=builder /app/target/*.jar app.jar
# Se expone el puerto que indicaste
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]