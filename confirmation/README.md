# Party Confirmation Service

## Pre-requisites
- Java 11
- Gradle 5.6.2+
- Apache Kafka

## How to start the application (Development Profile)
This assumes that your Apache Kafka is set to the default port `9092`. If not, just update the `application.yml` to target the
custom Apache Kafka port you have set. 

- Start up Apache Kafka.
- In your terminal you need to execute `./gradlew bootR --args="--spring.profiles.active=dev"`
