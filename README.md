# Project Base for Vaadin and Spring Boot

This project can be used as a starting point to create your own Vaadin application with Spring Boot. It contains all the
necessary configuration and some placeholder files to get you started.

## Running the Application

The project is a standard Maven project. To run it from the command line, type `mvn` and open http://localhost:8083 in
your browser.

## Project structure

The project follow
Maven's [standard directory layout structure](https://maven.apache.org/guides/introduction/introduction-to-the-standard-directory-layout.html):

- Under the `srs/main/java` are located Application sources
  - `Application.java` is a runnable Java application class and the app's starting point
  - `GreetService.java` is a Spring service class
  - `MainView.java` is an example Vaadin view
- Under the `srs/test` are located the TestBench test files
- `src/main/resources` contains configuration files and static resources
- The `frontend` directory in the root folder contains client-side dependencies and resource files. Example CSS styles
  used by the application are located under `frontend/styles`

