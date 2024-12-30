# Use Amazon Corretto 21 as the base image
FROM amazoncorretto:21 as builder

# Set the working directory
WORKDIR /app

# Copy the Gradle wrapper and the build.gradle files
COPY gradlew build.gradle settings.gradle /app/
COPY gradle /app/gradle

# Download the Gradle wrapper
RUN ./gradlew --version

# Copy the rest of the application source code
COPY src /app/src

# Build the application
RUN ./gradlew build

# Use a minimal base image for the final stage
FROM amazoncorretto:21-apline

# Set the working directory
WORKDIR /app

# Copy the built application from the builder stage
COPY --from=builder /app/build/libs/*.jar /app/app.jar

# Expose the application port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
