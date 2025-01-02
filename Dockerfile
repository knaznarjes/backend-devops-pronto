# Build stage
FROM maven:3.8.4-openjdk-17-slim AS build
WORKDIR /app
COPY pom.xml .
COPY mvnw .
COPY .mvn .mvn
COPY src src

# Package the application
RUN mvn clean package -DskipTests

# Run stage
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/target/back_end-0.0.1-SNAPSHOT.jar app.jar

# Expose the port of the application
EXPOSE 9090

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]