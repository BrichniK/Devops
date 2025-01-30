FROM openjdk:17-jdk-alpine

WORKDIR /app

COPY target/gestion-station-ski-1.0.jar /app/gestion-station-ski.jar

ENTRYPOINT ["java", "-jar", "/app/gestion-station-ski.jar"]

EXPOSE 8080