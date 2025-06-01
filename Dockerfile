FROM maven:3.9.4-eclipse-temurin-21

WORKDIR /app

COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN ./mvnw dependency:go-offline

COPY src ./src
RUN ./mvnw clean install -DskipTests

EXPOSE 8080

CMD sh -c 'java -jar target/*.jar'
