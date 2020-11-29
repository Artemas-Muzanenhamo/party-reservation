# reservation-entry

This application is solely for entering individuals wishing to reserve a 
spot at the upcoming party. This application will serve as a producer which will 
put all reservations onto a `Kafka` topic so that any consumers can subscribed
to that particular topic will be able to `consume` messages from it.
  

## Pre-requisites
- Java 11+
- Gradle 5.6.2+
- Apache Kafka

## How to start the application (Development Profile)
This assumes that your Apache Kafka is set to the default port `9092`. If not, just update the `application.yml` to target the
custom Apache Kafka port you have set. 

- Start up Apache Kafka.
- In the terminal run `./gradlew bootR --args='--spring.profiles.active=dev'` and the application should start up on port `8080`

## Understanding the API

Should you wish to understand the API before using it, there is [swagger](https://swagger.io/)
which is part of this application and you can access the _Swagger-UI_ at :

`http://localhost:8080/swagger-ui.html`


## Testing if all works fine
Should you wish to test if everything is set up correctly, you can try to add a reservation as below:

Using a REST client e.g. [postman](https://www.getpostman.com/) or [insomnia](https://insomnia.rest/) try to make 
a `POST` call to the reservation-entry API `localhost:8080/reservation` with the following JSON body:

```json
{
	"secret": "sudo",
	"name": "artemas",
	"surname": "muzanenhamo",
	"hasPlusOne": "false",
	"plusOne": "0"
}
```
The response should be a `201`:`Created` and should you wish to look at the data in kafka, you can just consume the topic 
your producer is sending messages to and the data you've just sent should be there.
