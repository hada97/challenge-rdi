# Restaurant Menu Item Status Management System

This project is designed to manage the status of Menu Items in a restaurant, based on the different types of Menu Items (PRODUCT, CHOICE, and VALUE MEAL) and their respective relationships. The system supports setting the status of the Menu Items to ACTIVE or INACTIVE based on certain rules.

## Technology Stack

- **Java 17**: The project is developed using Java 17.
- **H2 Database**: We use an H2 in-memory database for storing and retrieving menu item data.
- **Lombok**: Lombok is used to reduce boilerplate code for POJOs like getters, setters, and constructors.
- **JUnit**: Unit testing is implemented with JUnit to ensure correctness and stability of the application.
- **Mockito**: Mockito is used for mocking dependencies in unit tests to isolate test cases and ensure accurate testing of components.
- **Docker**: The application is containerized using Docker to ensure a consistent environment and simplify the deployment process.
- **Swagger**: Swagger is used to document and test the REST APIs, making it easier for developers and stakeholders to interact with the system.

## TYPES

- **PRODUCT (1)**: Regular menu items (e.g., Coke, Hamburger).
- **CHOICE (2)**: A menu item that contains other products as components (e.g., Drink Choice with Coke, Sprite).
- **VALUE MEAL (3)**: A combination of PRODUCTS and/or CHOICE items (e.g., Big Mac Meal with a drink and side choice).

### Status Rules:

- **PRODUCT**: A single product has a status of either ACTIVE or INACTIVE.
- **CHOICE**: A **CHOICE** item is ACTIVE if at least one of its components is ACTIVE.
- **VALUE MEAL**: A **VALUE MEAL** item is ACTIVE if all its components (whether PRODUCT or CHOICE) are ACTIVE.


### Setup Steps

1. Navigate to the project directory:

   ```bash
   cd <project-directory>

2. Build the project using Maven:

   ```bash
   mvn clean install

3. Running Unit Tests:

   ```bash
   mvn test


4. After starting the application, access the Swagger UI to view the endpoints and interact with the API:

   ```bash
   start http://localhost:8080/swagger-ui/index.html


![Diagram of System Architecture](/img.PNG)
