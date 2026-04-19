# ─── 1단계: 빌드 ───────────────────────────────
FROM eclipse-temurin:17-jdk-alpine AS builder

WORKDIR /app
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .
COPY src src

RUN chmod +x gradlew
RUN ./gradlew bootJar -x test

# ─── 2단계: 실행 ───────────────────────────────
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app
COPY --from=builder /app/build/libs/*.jar app.jar

# GCP 서비스 계정 JSON 마운트 경로
VOLUME /app/credentials

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
