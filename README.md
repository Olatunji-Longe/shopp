# Getting Started

### About this project
This project represents a small REST backend for a book-store, exposing several endpoints that are leveraged by it's peer
web app [shopp-web:](https://github.com/Olatunji-Longe/shopp-web).

---

### Tech Stack/Dependencies
This project utilizes the following technologies;
- Java 8
- Spring Boot (framework)
- Gradle (build tool)
- Embedded H2 (database)
- Liquibase (db migration)
- Caffeine (memory caching)
- Redis (Disabled, but can be optionally turned to replace caffeine for more robust standalone caching)
- A peer front-end application [shopp-web:](https://github.com/Olatunji-Longe/shopp-web) (written in React-16 webhooks) which consumes the APIs exposed by this service
- JUnit (testing)
- REST endpoints
    
---
    
### Execution
- Navigate to the root of the project and execute the following command;

```
    ./gradlew bootRun
```

All of the APIs are accessible via `http://localhost:8090/api/**` if you want to test with a REST client like Postman.
Please see the API documentation here:

   - Schema definitions : http://localhost:8090/api/v3/api-docs/
   - Endpoints: http://localhost:8090/api/swagger-ui/index.html?configUrl=/api/v3/api-docs/swagger-config#/
    
    
NOTE - All data exposed by the API endpoints are preloaded on the application's first start up. To reset the data, 
you can simply stop the application, delete the folder named `database` located directly under the root of the application, 
and start the application again. Now, you will be presented with fresh data from scratch.



---



### Reference Documentation
For further reference, please consider the following sections:

* [Official Gradle documentation](https://docs.gradle.org)
* [Spring Boot Gradle Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.2.5.RELEASE/gradle-plugin/reference/html/)
* [Spring for Apache ActiveMQ 5](https://docs.spring.io/spring-boot/docs/2.2.5.RELEASE/reference/htmlsingle/#boot-features-activemq)
* [Spring Boot Actuator](https://docs.spring.io/spring-boot/docs/2.2.5.RELEASE/reference/htmlsingle/#production-ready)
* [Spring Web](https://docs.spring.io/spring-boot/docs/2.2.5.RELEASE/reference/htmlsingle/#boot-features-developing-web-applications)
* [Spring Configuration Processor](https://docs.spring.io/spring-boot/docs/2.2.5.RELEASE/reference/htmlsingle/#configuration-metadata-annotation-processor)

### Guides
The following guides illustrate how to use some features concretely:

* [Java Message Service API via Apache ActiveMQ Classic.](https://spring.io/guides/gs/messaging-jms/)
* [Building a RESTful Web Service with Spring Boot Actuator](https://spring.io/guides/gs/actuator-service/)
* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/bookmarks/)

### Additional Links
These additional references should also help you:

* [Gradle Build Scans – insights for your project's build](https://scans.gradle.com#gradle)

