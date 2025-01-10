# Use a lightweight base image with OpenJDK 17
FROM openjdk:17-jdk-alpine
# Set the working directory
WORKDIR /app
# Set environment variables for the application
ENV DB_URL=jdbc:mysql://mysql:3306/E-BANK?createDatabaseIfNotExist=true \
    DB_USERNAME=root \
    DB_PASSWORD=root
# Expose the application port
EXPOSE 8085
# Copy the application JAR file into the working directory
COPY target/ebank-0.0.1.jar ebank.jar
# Use ENTRYPOINT to run the application
ENTRYPOINT ["java", "-jar", "ebank.jar"]
