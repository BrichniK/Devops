# Step 1: Use an official Maven image to build the app
FROM maven:3.8.6-openjdk-17-slim AS build

# Set the working directory inside the container
WORKDIR /app

# Step 2: Copy the pom.xml and download the dependencies
COPY pom.xml .

# Download dependencies (to cache dependencies layer)
RUN mvn dependency:go-offline

# Step 3: Copy the source code into the container
COPY src /app/src

# Step 4: Build the application with Maven
RUN mvn clean package -DskipTests

# Step 5: Use an OpenJDK image for running the application
FROM openjdk:17-slim

# Set the working directory inside the container
WORKDIR /app

# Step 6: Copy the packaged JAR file from the previous build stage
COPY --from=build /app/target/gestion-station-ski-1.0.jar /app/app.jar

# Step 7: Expose the port the application will run on (usually 8080 for Spring Boot)
EXPOSE 8080

# Step 8: Run the Spring Boot application
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
