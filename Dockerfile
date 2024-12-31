# Utilisez openjdk comme base d'image pour un environnement Java complet
FROM openjdk:17-jdk-slim

WORKDIR /app

# Copier les fichiers de configuration Maven
COPY pom.xml .
COPY mvnw .
COPY .mvn .mvn

# Copier le code source
COPY src src

# Packager l'application
RUN chmod +x mvnw && ./mvnw package -DskipTests

# Exposer le port de l'application
EXPOSE 9090

# Exécuter l'application
ENTRYPOINT ["java", "-jar", "target/back_end-0.0.1-SNAPSHOT.jar"]
