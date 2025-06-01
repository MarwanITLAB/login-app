FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY .mvn/ .mvn
COPY mvnw pom.xml ./

# 👉 Hier ist die wichtige Zeile!
RUN chmod +x mvnw

RUN ./mvnw dependency:go-offline

COPY src ./src
RUN ./mvnw clean install -DskipTests

EXPOSE 8080

CMD sh -c 'java -jar target/*.jar'
