# Pets' home (backend v1.0)
## Introduction
Pet's home is a website available for people to find 
and adopt the lost pets
- People can donate to the website and the website 
can guarantee the transparency of the currency
- People can apply to adopt the pets and the admin can review it 
- People can manage the adopted pet in the website 
- People can apply to become a volunteer
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

https://www.cnblogs.com/masonlee/p/12004638.html
https://pentacent.medium.com/nginx-and-lets-encrypt-with-docker-in-less-than-5-minutes-b4b8a60d3a71
https://medium.com/javarevisited/how-to-enable-https-in-spring-boot-application-78b77ba1d242

kafka & cdc sync
https://medium.com/dana-engineering/streaming-data-changes-in-mysql-into-elasticsearch-using-debezium-kafka-and-confluent-jdbc-sink-8890ad221ccf

https://medium.com/@jan_5421/how-to-add-an-elasticsearch-kafka-connector-to-a-local-docker-container-f495fe25ef72

https://www.baeldung.com/spring-kafka
https://www.baeldung.com/kafka-connectors-guide
https://contactsunny.medium.com/simple-apache-kafka-producer-and-consumer-using-spring-boot-41be672f4e2b
https://www.confluent.io/blog/kafka-connect-deep-dive-jdbc-source-connector/?_ga=2.247411716.1298723014.1696474530-926122659.1695350772&_gac=1.128856446.1696301627.Cj0KCQjw1OmoBhDXARIsAAAYGSHB_xRdYjDq-EO8LlRZmoi-pzOn13JydEAzyI2oZkFlHbb04Uv2MwQaAgrIEALw_wcB&_gl=1*z9nktv*_ga*OTI2MTIyNjU5LjE2OTUzNTA3NzI.*_ga_D2D3EGKSGD*MTY5NjQ3NDUzMC4xNS4xLjE2OTY0NzQ1MzYuNTQuMC4w


Register the MySQL Connector by sending an HTTP POST request to the Connect REST API:
curl -i -X POST -H "Accept: application/json" -H "Content-Type: application/json" \
  --data @./source-connector-config.json http://localhost:8083/connectors