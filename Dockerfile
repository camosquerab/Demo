FROM gradle:8.10.2-jdk21 AS build

WORKDIR /app

COPY build.gradle settings.gradle ./
COPY gradle ./gradle

RUN gradle build --no-daemon -x test || return 0

COPY src ./src

RUN gradle build --no-daemon -x test


FROM gradle:8.10.2-jdk21

RUN addgroup --system appgroup && adduser --system --ingroup appgroup appuser

WORKDIR /app

COPY --from=build /app/build/libs/*.jar app.jar

RUN chown appuser:appgroup app.jar

USER appuser

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]