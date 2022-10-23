# Pets' home (backend v1.0)
## Introduction
Pet's home is a website available for people to find 
and adopt the lost pets
- People can donate to the website and the website 
can guarantee the transparency of the currency
- People can apply to adopt the pets and the admin can review it 
- People can manage the adopted pet in the website 
- People can apply to become a volunteer
## Technical Notes: Installation
Install and process the following before running 'PetshomeApplication'
### Establish dependencies
run pom.xml file
### Download MySQL
Change the following in application.properties file accordingly:

spring.datasource.username=${MYSQL_USER:your_nominated_server's_name}
spring.datasource.password=${MYSQL_PWD:your_nominated_server's_password}

Create a database named "petshome" under the nominated server
Keep the server open before running 'PetshomeApplication'

### Download Redis
https://redis.io/docs/getting-started/

run redis-server
## Tech stack for backend
We used the following technology for the development of our backend

- Java Spring boot(eg. Hibernete, Spring data JPA)
- Java Spring Security
- Swagger
- Redis
- MySQL
- docker
- AWS EC2,S3, code commit
## Use case
![usecase.png](./picturefold/usecase.png)
## Database design
### Conceptual design
![database.png](./picturefold/database.png)

### physical design
![physical_design.png](./picturefold/physical_design.png)

## Architecture
![architecture.png](./picturefold/architecture.png)
