# Etapa 1: Construcción
FROM eclipse-temurin:17-jdk-alpine as build
WORKDIR /app

# Copiar archivos de Maven
COPY pom.xml .
COPY mvnw .
COPY .mvn .mvn

# Dar permisos de ejecución a mvnw
RUN chmod +x mvnw

# Descargar dependencias (esto se cachea)
RUN ./mvnw dependency:go-offline -B || true

# Copiar código fuente
COPY src ./src

# Compilar la aplicación
RUN ./mvnw clean package -DskipTests

# Etapa 2: Ejecución (imagen más liviana)
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Copiar el JAR compilado desde la etapa anterior
COPY --from=build /app/target/*.jar app.jar

# Exponer el puerto (Render usa la variable PORT)
EXPOSE 8080

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]
