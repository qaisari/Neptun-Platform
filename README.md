# Neptun-Platform Project

## Project Description

This project is a Spring Boot application designed for managing student data, marks, and subjects. It includes user authentication and authorization using Spring Security, allowing for different levels of access (e.g., authenticated users, administrators).

## Features

*   User authentication and registration.
*   Role-based authorization (e.g., ADMIN role).
*   Management of student information.
*   Display of charts and data related to students.
*   CRUD operations for student data.
*   Static content serving (CSS, JavaScript, images).

## Technologies Used

*   **Java**
*   **Spring Boot**: Framework for building stand-alone, production-grade Spring applications.
*   **Spring Security**: For authentication and authorization.
*   **Maven**: Dependency management and build automation.
*   **HTML/CSS/JavaScript**: For the frontend.
*   **Thymeleaf**: (Likely, given `templates` folder) Server-side Java template engine.
*   **H2 Database**: (Possible, for development) In-memory database.

## Getting Started

### Prerequisites

*   Java Development Kit (JDK) 17 or higher.
*   Maven 3.6.0 or higher.

### Installation

1.  **Clone the repository:**
    ```bash
    git clone <repository_url>
    cd Java-Seminar-Homework/demo
    ```

2.  **Build the project:**
    ```bash
    mvn clean install
    ```

3.  **Run the application:**
    ```bash
    mvn spring-boot:run
    ```

    The application will typically run on `http://localhost:8080`.

## Usage

*   **Home Page (`/`)**: Accessible to all, displays general information and images.
*   **Login Page (`/login`)**: Users can log in with their credentials.
*   **Register Page (`/register`)**: New users can create an account.
*   **Authenticated Pages (`/home`, `/contact`, `/datamenu/**`, `/charts`, `/crud/students/**`)**: Accessible after successful login.
*   **Admin Pages (`/admin/**`)**: Accessible only to users with the `ADMIN` role.

## Contributing

Feel free to fork the repository and submit pull requests. For major changes, please open an issue first to discuss what you would like to change.

## License

This project is licensed under the MIT License - see the LICENSE.md file for details. (If applicable, otherwise remove or update)
