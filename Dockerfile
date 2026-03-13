# ---------- BUILD STAGE ----------
FROM gradle:8.7-jdk21 AS builder

WORKDIR /app
COPY . .

RUN ./gradlew shadowJar --no-daemon

# ---------- RUNTIME STAGE ----------
FROM eclipse-temurin:21-jre-jammy

WORKDIR /app

COPY --from=builder /app/build/libs/*-all.jar app.jar

EXPOSE 7070
EXPOSE 9092

CMD ["java", "-jar", "app.jar"]