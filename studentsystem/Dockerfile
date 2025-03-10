# Base image mit Java 21 (falls du eine andere Version nutzt, passe es an)
FROM openjdk:21-jdk-slim

# Setze das Arbeitsverzeichnis im Container
WORKDIR /app

# Kopiere das JAR-File (Passe den Namen an dein Projekt an!)
COPY target/studentsystem-0.0.1-SNAPSHOT.jar app.jar

# Port, den Spring Boot nutzt
EXPOSE 8080

# Starte die Spring Boot Anwendung
ENTRYPOINT ["java", "-jar", "app.jar"]
