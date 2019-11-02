# party-reservation
An application to make party reservations. This application is solely for entering individuals wishing to reserve a 
spot at the upcoming party.

## Pre-requisites
- Java 11+
- Gradle 5.6.2+
- MongoDB

## How to start the application
This assumes that your mongodb is set to the default port `27017`. If not, just update the `application.yml` to target the
custom mongodb port you have set. 

- Start up MongoDB e.g. `sudo mongod` in the terminal.
- In the terminal run `./gradlew bootRun` and the application should start up on port `8080`

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
The response should be a `201`:`Created` and should you wish to look at the data in MongoDB, you can use a 
Mongo GUI like [robomongo](https://robomongo.org/) and you can view the data there.
