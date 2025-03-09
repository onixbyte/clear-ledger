# Use a minimal base image for the final stage
FROM amazoncorretto:21-alpine

ARG APP_VERSION=''
ENV ACTIVE_PROFILES=''

# Set the working directory
WORKDIR /app

# Copy the built application from the builder stage
COPY ./build/libs/clear-ledger-server-${APP_VERSION}.jar /app/app.jar

# Expose the application port
EXPOSE 8080

# Run the application
ENTRYPOINT ["/bin/sh", "-c", "java -jar -Dspring.profiles.active=$ACTIVE_PROFILES /app/app.jar"]
