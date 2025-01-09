FROM openjdk:17-jdk-alpine
EXPOSE 8085
ENV DB_URL=jdbc:mysql://mysql:3306/E-BANK?createDatabaseIfNotExist=true
ENV DB_USERNAME=root
ENV DB_PASSWORD=root
COPY target/ebank-0.0.1.jar ebank-0.0.1.jar
ENTRYPOINT ["java","-jar","/ebank-0.0.1.jar"]
