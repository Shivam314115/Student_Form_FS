# Student Form

This is a full-stack student registration application. The frontend is built with React and Vite, and the backend is built with Spring Boot, Spring Data JPA and MySQL.

## What this project does

- Provides a web form for students to register their details.
- Validates user input on the client side and on the backend.
- Stores student records in a MySQL database.
- Exposes a REST API endpoint to create and fetch students.

## Tech stack

- Frontend: React, Vite, Axios
- Backend: Spring Boot, Spring Data JPA, Hibernate
- Database: MySQL
- Java version: 25

## Backend details

The backend lives in the `backend` folder.

- `StudentFormApplication.java` starts the Spring Boot application.
- `StudentController.java` exposes the REST API at `/api/students`.
  - `GET /api/students` returns all students.
  - `POST /api/students` saves a new student record.
- `Student.java` is the JPA entity for the `students` table.
  - Fields include `name`, `email`, `mobileNumber`, `branch`, and `enrollmentNumber`.
  - Validation annotations are used to enforce required values and formats.
- The backend uses `spring.jpa.hibernate.ddl-auto=update` so the table schema is created or updated automatically during development.

## Frontend details

The frontend lives in the `frontend` folder.

- `src/components/StudentForm.jsx` renders the registration form.
- `src/services/StudentService.js` wraps the API calls to the backend.
- The form accepts:
  - full name
  - enrollment number
  - email address
  - mobile number
  - branch
- It validates fields before submitting and displays a status message after submission.

## Database configuration

The backend is configured to connect to MySQL using the values in `backend/src/main/resources/application.properties`.

By default, the file points to:

- `jdbc:mysql://localhost:3306/student_db`
- `spring.datasource.username=root`
- `spring.datasource.password=Shivam@3141`

If your local MySQL setup uses different credentials or database settings, update `backend/src/main/resources/application.properties` before starting the backend.

## How to start this project locally

### 1. Install prerequisites

- Java 25 or a compatible JDK
- MySQL server running locally
- Node.js and npm

### 2. Prepare the database

Create a database named `student_db` in MySQL, or change the database name in `backend/src/main/resources/application.properties`.

For example:

```bash
mysql -u root -p
CREATE DATABASE student_db;
EXIT;
```

### 3. Start the backend

Open a terminal in the `backend` folder and run:

```bash
cd backend
./mvnw spring-boot:run
```

The backend runs on port `8080` and exposes the student API at `http://localhost:8080/api/students`.

### 4. Start the frontend

Open a separate terminal in the `frontend` folder and run:

```bash
cd frontend
npm install
npm run dev
```

Vite will start the frontend on its default port, usually `5173`. Open the browser to the URL shown in the terminal.

### 5. Use the application

- Open the frontend URL shown by Vite.
- Fill in the registration form.
- Submit the form and verify that the backend stores the student record.

## Notes

- The frontend sends requests to `http://localhost:8080/api/students`.
- If the backend is not running or is configured on a different port, update the API base URL accordingly.
- The backend will automatically create or update the `students` table in the configured MySQL database during development.

