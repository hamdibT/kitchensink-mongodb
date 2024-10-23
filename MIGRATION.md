## Migration from Jakarta EE to Spring Boot
### Run the project
Be able to run locally the initial project
   + Clone the repository
   + Build the project
   + Install JBOSS 
   + deploy the war file

### Dependencies Migration
Updated the pom.xml to include Spring Boot dependencies.

### Annotations and Components Migration
   - Replaced Jakarta EE annotations with Spring Boot annotations.
   - Converted EJBs to Spring Beans using @Service, @Repository.
   - Replaced @Inject with Constructor Injection.

### Controllers Migration

 Converted Jakarta EE REST controllers to Spring Boot REST controllers using @RestController.

### Persistence Migration  
   - Replaced Jakarta EE JPA configuration with Spring Data JPA.
   - Updated entity classes and repositories to use Spring Data JPA annotations and interfaces.
### Tests Migration
    
Updated test cases to use Spring Boot testing framework.