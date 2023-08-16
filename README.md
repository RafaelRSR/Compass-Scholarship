
# Compass Scholarship RESTful API

This project is a RESTful API built using  Spring Boot that uses standard HTTP operations (GET, POST, PUT, DELETE) to facilitate the management of the Compass Scholarship Program. Our main goal with this web service was to be able to register information for the classes of the program, and we achieved it by enabling several operations for students, classes, staff and squads.


## Technologies applied:

☕️ Java 17

🍃 Spring Boot 3.0.9

🐬 MySQL Database


## Business Rules:



- Class must have a minimum of 15  students and a maximum of 30 .
- The class must have 3 statuses: waiting, started, finished.
- You can only register new students in the status: waiting.
- There must be an end to changing the status to finished.
- Each squad should have a maximum of 5  students and be as balanced as possible.
- To start the class you need 1 coordinator, 1 scrum master and 3 instructors.

## Running Locally

Clone the repository:

```bash
  git clone https://github.com/RafaelRSR/Compass-Scholarship.git
```

Navigate to the project directory:

```bash
  cd my-project
```

Build and run the application:

```bash
 ./mvnw spring-boot:run
```


The API will be accessible at:
``` 
http://localhost:8080
```



## Running Tests

1 - Make sure you have the project set up and running as explained in the Getting Started section.

2 - Open a terminal and navigate to the project's root directory.

3 - Run the tests using the following command:

```bash
  ./mvnw test
```

This command will execute all the test cases in the project and provide a summary of the results.

**Note: If you're using Windows, you can use mvnw.cmd instead of ./mvnw in the command.**

After the tests are completed, you'll see an output indicating the number of tests run, passed, and failed.

