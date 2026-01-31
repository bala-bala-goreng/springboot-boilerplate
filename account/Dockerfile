# Multi-stage build: Build stage
FROM maven:3.9-eclipse-temurin-21-alpine AS builder

WORKDIR /workspace

# First, build the core library (local dependency)
WORKDIR /workspace/core
COPY core/pom.xml .
COPY core/src ./src
RUN mvn clean install -DskipTests -B

# Now build this service
WORKDIR /workspace/account
COPY account/pom.xml .
COPY account/src ./src
RUN mvn clean package -DskipTests -B

# Runtime stage
FROM eclipse-temurin:21-jre-alpine

ENV JAVA_OPTS=""

WORKDIR /app

RUN apk add --no-cache tzdata

# Copy JAR from builder stage
COPY --from=builder /workspace/account/target/*.jar /app/app.jar

EXPOSE 8081

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /app/app.jar"]
