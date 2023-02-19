# KameleoonTechnicalTask

## Service for publishing quotes

## Technology stack:

* Spring boot
  * Spring Data JPA
  * Spring Web
  * Spring Security
  * Spring Security Test
* Lombok
* H2 database
* JUnit5
* OpenAPI

## OpenAPI documentation:

https://app.swaggerhub.com/apis-docs/Ulashlo/KameleoonTechnicalTaskApi/1.0

## Docker-compose file for deploy:

```
version: '3.8'
services:
  server:
    image: vanikolsky/kameleoon-trial-task
    container_name: server
    ports:
      - "8080:8080"
```
