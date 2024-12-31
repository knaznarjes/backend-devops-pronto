FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

# Copy Maven configuration files
COPY pom.xml .
COPY mvnw .
COPY .mvn .mvn

# Copy source code
COPY src src

# Package the application
RUN chmod +x mvnw && ./mvnw package -DskipTests

# Run the application
EXPOSE 9090
ENTRYPOINT ["java", "-jar", "target/back_end-0.0.1-SNAPSHOT.jar"]