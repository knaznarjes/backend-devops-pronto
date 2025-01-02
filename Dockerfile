FROM openjdk:17-jdk-slim AS builder

WORKDIR /app

# Copier les fichiers de configuration Maven
COPY pom.xml .
COPY mvnw .
COPY .mvn .mvn

# Copier le code source
COPY src src

# Donner les droits d'exécution au wrapper Maven
RUN chmod +x mvnw

# Compiler l'application
RUN ./mvnw clean package -DskipTests

# Image finale
FROM openjdk:17-jdk-slim

WORKDIR /app

# Copier uniquement le JAR de l'application depuis l'étape de build
COPY --from=builder /app/target/back_end-0.0.1-SNAPSHOT.jar .

# Exposer le port de l'application
EXPOSE 9090

# Exécuter l'application
ENTRYPOINT ["java", "-jar", "back_end-0.0.1-SNAPSHOT.jar"]