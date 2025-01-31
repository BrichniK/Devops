# Use the official OpenJDK 17 image as the base image
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Install necessary packages (including default MySQL client)
RUN apt-get update && \
    apt-get install -y default-mysql-client && \
    apt-get clean

# Copy only the built JAR file into the container
COPY target/gestion-station-ski-1.0.jar /app/gestion-station-ski.jar

# Expose the correct port
EXPOSE 8089

# Command to run the application
ENTRYPOINT ["java", "-jar", "/app/gestion-station-ski.jar"]