FROM --platform=linux/amd64 maven:3.8.4-openjdk-17 AS build

COPY /src /app/src
COPY /pom.xml /app
RUN mvn -f /app/pom.xml clean package -Dmaven.test.skip

FROM --platform=linux/amd64 openjdk:17
COPY --from=build /app/target/rinha-backend-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
