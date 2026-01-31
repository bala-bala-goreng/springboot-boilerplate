# Multi-stage build: Build stage
FROM maven:3.9-eclipse-temurin-21-alpine AS builder

WORKDIR /workspace

# Build this service (eureka doesn't depend on core)
WORKDIR /workspace/eureka
COPY eureka/pom.xml .
COPY eureka/src ./src
RUN mvn clean package -DskipTests -B

# Runtime stage
FROM eclipse-temurin:21-jre-alpine

ENV JAVA_OPTS=""

WORKDIR /app

RUN apk add --no-cache tzdata

# Copy JAR from builder stage
COPY --from=builder /workspace/eureka/target/*.jar /app/app.jar

EXPOSE 8761

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /app/app.jar"]
