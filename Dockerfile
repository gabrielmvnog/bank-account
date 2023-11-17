FROM gradle:8.4.0-jdk17-alpine as BUILDER

WORKDIR /app
COPY --chown=gradle:gradle . /app

RUN gradle bootJar

FROM openjdk:17-slim as RUNNER

WORKDIR /app
COPY --from=BUILDER /app/build/libs/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
