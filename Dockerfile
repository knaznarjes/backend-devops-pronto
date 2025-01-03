# Build stage with cache mounted
FROM maven:3.8.4-openjdk-17-slim AS builder
WORKDIR /app
# Copy only pom.xml first to cache dependencies
COPY pom.xml .
RUN --mount=type=cache,target=/root/.m2 mvn dependency:go-offline

# Copy source and build
COPY src ./src
RUN --mount=type=cache,target=/root/.m2 mvn package -DskipTests

# Minimal runtime image
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar
# Add spring boot runtime options
ENTRYPOINT ["java", "-Xms512m", "-Xmx512m", "-jar", "app.jar"]