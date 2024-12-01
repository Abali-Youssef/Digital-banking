# Digital Banking Backend

This is the backend module of the **Digital Banking Application**, built with **Spring Boot** to handle server-side operations, API integrations, and real-time transaction processing.

## Features

- **User Management**: Authentication, authorization, and profile management.
- **Account Management**: Manage user accounts, balances, and transaction history.
- **Real-Time Transactions**: Secure and efficient transaction handling.
- **RESTful APIs**: Seamless integration with the frontend.
- **API Documentation**: Easily explore and test endpoints via Swagger.

## Tech Stack

- **Programming Language**: Java
- **Framework**: Spring Boot
- **Database**: MySQL
- **ORM**: Hibernate
- **Dependency Management**: Maven
- **API Documentation**: Swagger
- **Version Control**: Git

## Prerequisites

Ensure the following are installed on your system:

- Java 17 or higher
- Maven 3.8 or higher
- MySQL Server
- Git

## Setup Instructions

### 1. Clone the Repository

```bash
git clone https://github.com/Abali-Youssef/Digital-banking-back.git
cd Digital-banking-back
```
### 2. Configure Application Properties

Edit the `application.properties` file located in `src/main/resources`:

- Set your database connection details (e.g., `spring.datasource.url`, `username`, `password`).
- Adjust other application-specific configurations as needed.
### 3. Build and Run the Application

Build the project and start the server:

```bash
mvn clean install
mvn spring-boot:run
```
### 4. Access the API Documentation

Open your browser and navigate to [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html) to view and test the API.

## Folder Structure

- **`src/main/java`**: Application source code
- **`src/main/resources`**: Configuration files
- **`pom.xml`**: Maven project file

