# party-reservation
An application to make party reservations.

![Java CI](https://github.com/Artemas-Muzanenhamo/party-reservation/workflows/Java%20CI/badge.svg?branch=develop)

## Design
<img src="https://user-images.githubusercontent.com/29547780/91648722-e94c2e80-ea62-11ea-8e1c-5157bc36b6db.png" alt="#"/>

## The Problem
Consumer microservices typically are bound to know or have some level of knowledge about the domain of the Producer microservice
especially when the communication between a Consumer microservice and a Producer microservice is done via HTTP. The problem of really
not coupling your microservices becomes a challenge especially in the case when there are more Producer microservices that the Consumer
microservice depends on.    

## The Goal
So the idea here is that we can decouple microservices with through messaging as opposed to communication via HTTP. 
The other great thing about this approach is that the producing microservice
will not have to care what type technology/stack/language the consumer uses as the consumer will only just consume/take
messages from kafka. The *Reservation-Entry* service is the producer and sends messages to Kafka and the *Confirmation*
service is the consumer application which will consume messages from Kafka and send Server Sent Events to the client.

The *Reservation-Entry* service is a Reactive Spring Application that uses the normal Spring Kafka to publish data onto
the Kafka queue that our *Confimation* service will consume. This application will be in charge of writing/publishing to the queue.

The *Confirmation* service is a Reactive Spring Application that uses the [Project Reactor](https://projectreactor.io/) 
Kafka library to consume from kafka reactively and send data to the client reactively. This application will only be reading events from the queue
only. 
 
## Swagger

### Reservation Confirmation Service
http://localhost:8081/webjars/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config

## Docker
* To start both microservices in docker you need to run: 
`docker-compose up` or `docker-compose up -d` without the daemon attached to your
console.
* This will start the confirmation service on `localhost:8081` and the reservation service on `localhost:8080`
* To stop the services from running while the daemon is attached to your console
you need to run `ctrl + c`.

## Testing
* Testing on both confirm microservice and reservation-entry microservice is done at the unit test level.
