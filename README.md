# Meeting Planner
## Overview
This is web application in which you can: 
 - Plan meetings with your colleagues
 - Move or remove meetings
 - See your or your colleague meetings
 - See availability of your colleague in specific time
## Architecture
### Tech stack
Application is written in Java 17 and Spring Boot 3 on the backend and Angular 15 and Typescript on the frontend.
### Communication and security
Backend provides RESTful api which is secured by using JWT token. User needs to login to access other api routes. For the simplicity of application you can log in but you can't create accounts. 
But I created few accounts for you to use with password `123456`:
 - asia@t.pl
 - kasia@t.pl
 - basia@t.pl
## Instruction
### Frontend
Frontend is written in Angular so to start the application you have to install dependencies first: `npm install` and then start development server with command `ng serve`. Application will be running on port `4200`.
### Backend
Backend is an Spring web application. To run application please find MeetingPlannerApplication.java and run main class. Optionally you can run `./gradlew bootRun` to run the application.
