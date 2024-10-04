

# Etapa 1: Construir la aplicación usando Gradle con Java 21
FROM gradle:8.10.2-jdk21 AS build

# Establecer el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copiar los archivos de configuración de Gradle y descargar dependencias
COPY build.gradle settings.gradle ./
COPY gradle ./gradle

# Descargar las dependencias sin compilar
RUN gradle build --no-daemon -x test || return 0

# Copiar el resto del código fuente
COPY src ./src

# Compilar y empaquetar la aplicación
RUN gradle build --no-daemon -x test

# Etapa 2: Ejecutar la aplicación usando una imagen JDK 21 ligera
FROM gradle:8.10.2-jdk21

# Crear un usuario no root para ejecutar la aplicación
RUN addgroup --system appgroup && adduser --system --ingroup appgroup appuser

# Establecer el directorio de trabajo
WORKDIR /app

# Copiar el JAR construido desde la etapa de construcción
COPY --from=build /app/build/libs/*.jar app.jar

# Cambiar la propiedad del JAR al usuario no root
RUN chown appuser:appgroup app.jar

# Cambiar al usuario no root
USER appuser

# Exponer el puerto en el que la aplicación se ejecuta (ajusta si es necesario)
EXPOSE 8080

# Definir el punto de entrada para el contenedor
ENTRYPOINT ["java", "-jar", "app.jar"]