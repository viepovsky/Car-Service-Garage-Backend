# Car Service Garage
Car Service Garage is an intuitive and user-friendly application that allows users to book appointments for car repairs or services at various automotive workshops located in different cities. 
Users can easily select the workshop of their choice and the specific service they want to have performed on their car. 
The application offers a convenient way to browse the history of reserved services and make changes to existing reservations.
The backend version of the application provides all necessary components, including classes for data storage, services, and endpoints. 
Additionally, the application leverages the power of two external APIs - Car API and Weather API - to provide comprehensive information on car details and weather forecasts for the workshop locations.

## Frameworks

The application uses popular frameworks such as Spring Boot, Spring Data JPA, Spring Security, Hibernate, Validation-API, H2 database.
The application's frontend was developed with Vaadin and designed primarily to show capabilities of the backend. 
In result the visual design may not be as good as other parts of the application. 

## Here is backend, link to frontend

This repository contains the backend of Car Service Garage. To function properly, it must be run in conjunction with the frontend version, which can be found here: [**GitHub**](https://github.com/viepovsky/Final-App-Frontend).

## Usage of external API

At the moment, the application is using two external API: 
- Car API to provide car details such as the year, make, model, and type.
- Weather API to retrieve a 13-day forecast for the garage location.

## How to run

To run whole application, you need to first run the backend by running `AppBackendApplication` class. Once that is done, to run the frontend, simply type `mvnw` (Windows) or `./mvnw` (Mac & Linux) in terminal IDE. Then, open http://localhost:8081 in your browser to access the application. 

In case of problems on Mac with the error message `zsh: permission denied: ./mvnw`, simply type `chmod +x ./mvnw` in terminal IDE to make the file executable, and then type `./mvnw` again to start the application.

## Logging to site, initial data

The backend of Car Service Garage contains initial data to demonstrate the application's capabilities. After testing with the provided user account, you can add your own to test the application with your own data. 

To log in, use the username: `testuser` and the password: `testpassword`
