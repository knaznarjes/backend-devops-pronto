FROM maven:3.8.4-openjdk-17-slim AS builder

WORKDIR /app

# Copy Maven configuration files
COPY pom.xml .
COPY mvnw .
COPY .mvn .mvn

# Copy source code
COPY src src

# Build application
RUN mvn clean package -DskipTests

# Final image
FROM openjdk:17-jdk-slim

WORKDIR /app

# Install curl for healthcheck
RUN apt-get update && apt-get install -y curl && rm -rf /var/lib/apt/lists/*

# Copy JAR from builder stage
COPY --from=builder /app/target/back_end-0.0.1-SNAPSHOT.jar .

# Expose application port
EXPOSE 9090

# Run application
ENTRYPOINT ["java", "-jar", "back_end-0.0.1-SNAPSHOT.jar"]