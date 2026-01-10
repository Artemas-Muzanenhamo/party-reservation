# Party Reservation
An application to make party reservations.

[![Java CI](https://github.com/Artemas-Muzanenhamo/party-reservation/actions/workflows/gradle.yml/badge.svg)](https://github.com/Artemas-Muzanenhamo/party-reservation/actions/workflows/gradle.yml)

## Design
<img src="https://user-images.githubusercontent.com/29547780/91648722-e94c2e80-ea62-11ea-8e1c-5157bc36b6db.png" alt="#"/>

## The Use Case
I want to make a party reservation through the **Reservation Entry** service, and have that reservation asynchronously propagated to the **Reservation Confirmation** service. This ensures that when the doorman checks the guest list on their device, my RSVP appears without requiring direct communication between the two services. The interaction should be seamless, event-driven, and decoupled. Each service operates independently while exchanging information through events.

## The Problem
Traditional microservice communication often relies on synchronous HTTP calls, which introduce tight coupling between services. In such setups, consumer services must understand the producer’s domain and API contracts, making them fragile and harder to scale. As the number of producers grows, this dependency web becomes increasingly complex, limiting flexibility and introducing integration overhead. Achieving true autonomy and resilience in microservices requires eliminating these direct dependencies.

## The Goal
The objective is to decouple microservices by adopting a **messaging-driven architecture** using **Kafka** as the event backbone. Instead of REST-based communication, services publish and consume events asynchronously. The **Reservation Entry** service acts as a **publisher**, emitting reservation events to Kafka without any knowledge of who consumes them. The **Reservation Confirmation** service acts as a **subscriber**, reacting to these events and updating the client in real time via **Server-Sent Events (SSE)**. This approach ensures:
- **Loose coupling**: Producers and consumers remain independent.
- **Technology agnosticism**: Consumers can use any stack or language.
- **Scalability and resilience**: New consumers can subscribe without impacting existing producers.
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
