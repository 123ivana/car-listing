# Car Listing application

Service is used for tracking Car listings

## Technologies
- Java
- Spring Boot
- Kafka
- Elasticsearch 
- Docker
- Maven

## Getting Started
Project is built as a mono repository for three modules:

- car-listing-producer
- car-listing-consumer
- car-listing-commons

This project is build with Maven and has support for Docker builds.

Parent can be build with Maven run `mvn clean install`.

Single modules can be build with Maven run `mvn clean install -projects car-listing-commons,car-listing-producer -Dmaven.test.skip=true`

### Run the project locally
Services car-listing-producer and car-listing-consumer can be run locally via:
```shell
mvn spring-boot:run
```

#### Docker & Docker Compose
Services can be build and run using docker compose:
```shell
docker-compose up --build
```
Docker compose will start car-listing-producer and car-listing-consumer with kafka, elasticsearch, zookeeper.

## Deployment
This services are deployable through a Docker image.

### Configuration

The configuration is done by environments variables.


| Environment variables name | Description                 | Default values | Possible values |  
|----------------------------|-----------------------------|----------------|-----------------|
| KAFKA_HOST                 | Specify kafka host          |                | `localhost:9092` | 
| ELASTICSEARCH_HOST         | Specify elasticsearch host  |                | `localhost:9200` |

