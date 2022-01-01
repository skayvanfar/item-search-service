# Assignment

Using your favorite Go or Java framework / libraries build a service, that will accept a request with text parameter on input. It will return maximum of 5 books and maximum of 5 albums that are related to the input term. The response elements will only contain title, authors(/artists) and information whether it's a book or an album. For albums please use the iTunes API. For books please use Google Books API. Sort the result by title alphabetically. Make sure the software is production-ready from resilience, stability and performance point of view. The stability of the downstream service may not be affected by the stability of the upstream services. Results originating from one upstream service (and its stability /performance) may not affect the results originating from the other upstream service.

Make sure the service:

- Your service needs to respond within a minute;
- is self-documenting
- exposes metrics on response times for upstream services
- exposes health check
- Limit of results on upstream services must be configurable per environment and preconfigured to 5.

Bonus points:
- Think about resilience
- Think about concurrency

Please document how we can run it. Please shortly document your justification of technology / mechanism choice.

--------------------------------------------------------------------------------------------------------------------------
# Item-Search-service
##
Item Search API retrieves data from iTunes Store and Google Books.
It uses resilience4j for the service to be resilient when the iTunes api and google api do not respond or respond with latency mort than 3 seconds(it is configurable)
Errors (including timeouts) from any of the upstream services won't affect user's request. resilience4j also gives the concurrency to call each API.

##suggestion
another approach is By using Spring WebFlux and Spring WebClient.

## Software needed
1.	Apache Maven (http://maven.apache.org).
2.	Docker (http://docker.com).
3.	Git Client (http://git-scm.com).

## Building the Docker Image
To build the code for project as a docker image, open a command-line window change to the directory where you have downloaded the project source code.

Run the following maven command.  This command will execute the [Spotify docker plugin](https://github.com/spotify/docker-maven-plugin) defined in the pom.xml file.  

   **mvn clean package docker:build**

If everything builds successfully you should see a message indicating that the build was successful.

## Running the Application

Now we are going to use docker-compose to start the actual image.  To start the docker image,
change to the directory containing  your project source code.  Issue the following docker-compose command:

   **docker-compose -f compose/common/docker-compose.yml up**

If everything starts correctly you should see a bunch of spring boot information fly by on standard out.  At this point all of the services needed for the project code will be running.

## how to call API
The Application exposed a REST Endpoint for searching Albums and Books

API Request: Http Method : GET

REST Endpoint URL: 

http://localhost:8080/api/items?term=reza

## API Documents
http://localhost:8080/swagger-ui.html

## API healthChecks
Shows health information of running API and application.

http://localhost:8080/actuator/health

## API metrics
Shows "metrics" information of the running API and application. 

http://localhost:8080/actuator/metrics/book.api.in.seconds

http://localhost:8080/actuator/metrics/album.api.in.seconds

## profile
Application used spring.profiles.active Environment property
By default dev profile is active. 
