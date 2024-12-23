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

# Copy the built JAR from the build stage
COPY --from=build /app/target/card-0.0.1-SNAPSHOT.jar /app/card-0.0.1-SNAPSHOT.jar

# Command to run the application
CMD ["java", "-jar", "card-0.0.1-SNAPSHOT.jar"]