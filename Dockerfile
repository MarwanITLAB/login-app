FROM eclipse-temurin:21-jdk

# Arbeitsverzeichnis setzen
WORKDIR /app

# Maven Wrapper kopieren
COPY .mvn/ .mvn
COPY mvnw pom.xml ./

# Maven Wrapper ausführbar machen
RUN chmod +x mvnw

# Abhängigkeiten auflösen und Projekt bauen (ohne Tests)
RUN ./mvnw clean install -DskipTests

# Quellcode kopieren
COPY src ./src

# App final bauen (optional, falls vorheriger Schritt nicht reicht)
RUN ./mvnw package -DskipTests

# Port freigeben
EXPOSE 8080

# App starten
CMD ["java", "-jar", "target/*.jar"]
