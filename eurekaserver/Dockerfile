# Build stage
FROM maven:3.9.9-amazoncorretto-21-alpine AS build

# Set the working directory
WORKDIR /app

# Copy the pom.xml and source code
COPY pom.xml .
COPY src ./src

# Build the application
RUN mvn clean package -DskipTests

# Run stage
FROM amazoncorretto:21-alpine-jdk

# Set the working directory
WORKDIR /app

# Update and install curl using apk
RUN apk add --no-cache curl

# Copy the built JAR from the build stage
COPY --from=build /app/target/eurekaserver-0.0.1-SNAPSHOT.jar /app/eurekaserver-0.0.1-SNAPSHOT.jar
COPY --from=build /app/target/opentelemetry-javaagent.jar /app/opentelemetry-javaagent.jar

# Command to run the application
CMD ["java", "-javaagent:/app/opentelemetry-javaagent.jar", "-jar", "eurekaserver-0.0.1-SNAPSHOT.jar"]