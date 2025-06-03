# Verwende ein leichtes Java-Image mit JDK 21
FROM eclipse-temurin:21-jdk

# Setze das Arbeitsverzeichnis im Container
WORKDIR /app

# Kopiere Maven Wrapper Dateien und pom.xml
COPY .mvn/ .mvn
COPY mvnw pom.xml ./

# Erlaube Ausführung des Wrapper-Skripts
RUN chmod +x mvnw

# Lade alle Abhängigkeiten vorab herunter (offline build preparation)
RUN ./mvnw dependency:go-offline

# Kopiere den restlichen Quellcode
COPY src ./src

# Baue das Projekt ohne Tests
RUN ./mvnw clean install -DskipTests

# Öffne Port 8080
EXPOSE 8080

# Starte das Spring Boot Jar
CMD ["java", "-jar", "target/login-0.0.1-SNAPSHOT.jar"]
