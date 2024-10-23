# KitchenSink Project

## Overview

The KitchenSink project is a Spring Boot application that demonstrates various functionalities including dbMember registration, validation, and exception handling. It uses Spring Data JPA for database interactions, Spring MVC for web layer, and JUnit and Mockito for testing.
Initially, the application is configured to use an in-memory H2 database for testing purposes. The database schema is created automatically when the application starts.

We have migrated the project from Jboss, jakarta ee to Spring Boot.

We have added the following features:
- Spring Boot
- split the project into layers (Controller, Service, Repository and Model)
- Separate the backend from the frontend
- Possibility to use API and Swagger
- Use of Testcontainers for integration tests
- Use of p6spy for logging SQL queries
- Use of MongoDB
- Migration of data from relational DB to MongoDB

The frontend is an Angular application that consumes the backend REST API. It allows users to register a member, list all members.
The frontend is located in https://github.com/hamdibT/kitchensink-frontend

## Technologies Used

- Java
- Spring Boot
- Spring Data JPA
- Spring Data MongoDB
- Spring MVC
- Maven
- Testcontainers
- H2 Database
- JUnit 5
- Mockito
- p6spy (for logging SQL queries)
- Angular(for the frontend)

## Setup and Installation

1. **Clone the repository:**
   ```sh
   git clone https://github.com/hamdibT/kitchensink-mongodb.git
   cd kitchensink

2. **Build the project:**
   ```sh
   mvn clean install

3. **Run the backend application:**
   ```sh
   cd cd /path/to/your/project
   docker-compose up
   mvn spring-boot:run

4. **Run the backend application:**
    ```sh
    cd kitchensink-frontend
    ng serve
     
    
5. **Access the application:**

   Open a web browser and navigate to
   ```sh
    http://localhost:4200/`

6. **Access the Swagger API:**

   Open a web browser and navigate to
     ```sh
        http://localhost:8085/swagger-ui.html
   
There's 2 ways to use this application:
1. Relational DB
2. MongoDB

To be able to do that you will need to change basURL value in the file 

      kitchensink-frontend/src/environments/environment.ts
      baseURL:"http://localhost:8085/mongo/members" -- to use mongoDB
      baseURL:"http://localhost:8085/members" -- to use relational DB

## Migration
to migrate data you should launch from swagger 
      http://localhost:8085/swagger-ui.html and launch the migration endpoint

## What's next
- How to handle large amount of data (avoid Typed object for mongoDB and use directly the HashMap)
- Run migration in simulation mode (without committing) in order to analyse the impact on the system
- How to handle the migration of the data from the relational DB to the mongoDB in case of large volume (findAll crash the JVM)
- During migration of large volume we can consider using bulk insert
- Frontend should be adapted in case of multiple rows and columns instead of the actual html table, we should consider using a modern grid (agGrid) and adopt pagination
- How to monitor the app 


   

