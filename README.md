# KameleoonTechnicalTask

## Technology stack:

* Spring boot
  * Spring Data JPA
  * Spring Web
  * Spring Security
  * Spring Secirity Test
* Lombok
* H2 database
* JUnit5


## Docker-compose file for deploy:

```
version: '3.8'
services:
  server:
    image: vanikolsky/kameleoon_task_image_test
    container_name: server
    ports:
      - "8080:8080"
```
