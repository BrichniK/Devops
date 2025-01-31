# Use an official Java runtime as a parent image
FROM openjdk:17-jdk-alpine

# Set the working directory inside the container
WORKDIR /app

# Copy the local jar file to the container's /app directory
COPY target/gestion-station-ski-1.0.jar /app/gestion-station-ski.jar

# Run the application
ENTRYPOINT ["java", "-jar", "/app/gestion-station-ski.jar"]

# Expose the port the app runs on
EXPOSE 8080