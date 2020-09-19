# party-reservation
An application to make party reservations.

![Java CI](https://github.com/Artemas-Muzanenhamo/party-reservation/workflows/Java%20CI/badge.svg?branch=develop)

## Design
<img src="https://user-images.githubusercontent.com/29547780/91648722-e94c2e80-ea62-11ea-8e1c-5157bc36b6db.png" alt="#"/>

So the idea here is that we can decouple microservices with kafka. The other great thing is that the producing microservice
will not have to care what type technology/stack/language the consumer uses as the consumer will only just consume/take
messages from kafka. The *Reservation-Entry* service is the producer and sends messages to Kafka and the *Confirmation*
service is the consumer application which will consume messages from Kafka and send Server Sent Events to the client.

The *Confirmation* service is a Reactive Spring application that uses the [Project Reactor](https://projectreactor.io/)
Reactor Kafka library to consumer from kafka reactively and send data to the client reactively.   
 
## Swagger

### Reservation-Entry Service
http://localhost:8080/swagger-ui.html#/

### Reservation Confirmation Service
http://localhost:8081/webjars/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config

## Docker

* To start both microservices in docker you need to run: 
`docker-compose up`
* This will start the confirmation service on `localhost:8081` and the reservation service on `localhost:8080`
