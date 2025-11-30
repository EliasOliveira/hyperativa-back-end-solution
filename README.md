# Hyperativa Back-End API

This is a Spring Boot REST API developed for the Hyperativa challenge. It provides endpoints for card registration, lookup, and authentication using JWT. The API is documented using Swagger UI and uses H2 as the default in-memory database.

---

## 🛠️ Requirements

- Java 17+
- Gradle
- Git
- IntelliJ IDEA or another Java IDE

---

## 🚀 Setup & Run Instructions

### 1. Clone the repository

```bash
git clone https://github.com/EliasOliveira/hyperativa-back-end-solution.git &&
cd hyperativa-back-end-solution
```

### 2. Build the project
```bash
./gradlew clean build
```

### 3. Run the application
```bash
./gradlew bootRun
```

### 4. See the documentation
Open the following URL in your browser: http://localhost:8080/swagger-ui/index.html