FROM eclipse-temurin:17-jre-alpine

WORKDIR /app
COPY build/libs/*.jar app.jar
 
# GCP 서비스 계정 JSON 마운트 경로
VOLUME /app/credentials
 
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]

# docker build -t why48382/onsoom-be:latest .
# docker push why48382/onsoom-be:latest