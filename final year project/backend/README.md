# FundTracker Backend

A robust REST API for the FundTracker accounting management system, built with **Spring Boot 3.2** and **MongoDB**.

---

## Tech Stack

| Layer | Technology |
|---|---|
| Framework | Spring Boot 3.2.0 |
| Language | Java 17 |
| Database | MongoDB |
| Security | Spring Security + JWT (jjwt 0.11.5) |
| Validation | Spring Boot Starter Validation |
| Build Tool | Maven |
| Boilerplate | Lombok |

---

## Project Structure

```
backend/
├── src/main/java/com/fundtracker/
│   ├── FundTrackerApplication.java       # Entry point
│   ├── config/
│   │   └── WebSecurityConfig.java        # CORS + JWT security config
│   ├── controller/
│   │   ├── AuthController.java           # Login endpoint
│   │   ├── DashboardController.java      # Dashboard summary
│   │   ├── MasterController.java         # Master data (clients, banks, etc.)
│   │   ├── ExportInvoiceController.java  # Export invoices
│   │   ├── PurchaseInvoiceController.java
│   │   ├── RetailInvoiceController.java
│   │   ├── DebitNoteController.java
│   │   ├── ExportReceiptController.java
│   │   ├── ReceiptController.java
│   │   ├── RetailReceiptController.java
│   │   ├── PaymentController.java
│   │   ├── IncentivesController.java
│   │   ├── ReportController.java
│   │   └── UserController.java
│   ├── model/                            # MongoDB document models
│   ├── repository/                       # Spring Data MongoDB repositories
│   ├── service/                          # Business logic layer
│   ├── dto/                              # Request/Response DTOs
│   ├── security/                         # JWT filter, UserDetailsService
│   └── exception/                        # Global exception handler
├── pom.xml
└── src/main/resources/
    └── application.properties
```

---

## Prerequisites

- Java 17 or higher
- Maven 3.8+
- MongoDB 6.0+ (running locally or a remote URI)

---

## Getting Started

### 1. Clone the repository

```bash
git clone https://github.com/your-username/fundtracker.git
cd fundtracker/backend
```

### 2. Configure application properties

Create or edit `src/main/resources/application.properties`:

```properties
# MongoDB
spring.data.mongodb.uri=mongodb://localhost:27017/fundtracker

# JWT
app.jwt.secret=YOUR_SECRET_KEY_HERE
app.jwt.expiration=86400000

# CORS (comma-separated allowed origins)
app.cors.allowed-origins=http://localhost:3000

# Server port
server.port=8080
```

> **Note:** Never commit secrets to version control. Use environment variables or a `.env` file in production.

### 3. Build and run

```bash
# Build the project
mvn clean install

# Run the application
mvn spring-boot:run
```

The server will start at `http://localhost:8080`.

---

## API Endpoints

All endpoints (except `/auth/login`) require a valid JWT token in the `Authorization: Bearer <token>` header.

| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/auth/login` | Authenticate and receive JWT token |
| `GET` | `/api/dashboard` | Get dashboard summary |
| `GET/POST/PUT/DELETE` | `/api/master/**` | Manage master data (clients, banks, suppliers, etc.) |
| `GET/POST/PUT/DELETE` | `/api/export-invoices/**` | Export invoice management |
| `GET/POST/PUT/DELETE` | `/api/purchase-invoices/**` | Purchase invoice management |
| `GET/POST/PUT/DELETE` | `/api/retail-invoices/**` | Retail invoice management |
| `GET/POST/PUT/DELETE` | `/api/debit-notes/**` | Debit note management |
| `GET/POST/PUT/DELETE` | `/api/receipts/**` | Receipt management |
| `GET/POST/PUT/DELETE` | `/api/export-receipts/**` | Export receipt management |
| `GET/POST/PUT/DELETE` | `/api/retail-receipts/**` | Retail receipt management |
| `GET/POST/PUT/DELETE` | `/api/payments/**` | Payment management |
| `GET/POST/PUT/DELETE` | `/api/incentives/**` | Incentives management |
| `GET` | `/api/reports/**` | Financial reports |
| `GET/POST/PUT/DELETE` | `/api/users/**` | User management |

### Authentication Example

```bash
# Login
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username": "admin", "password": "password"}'

# Use the returned token for subsequent requests
curl http://localhost:8080/api/dashboard \
  -H "Authorization: Bearer <your-jwt-token>"
```

---

## Security

- Stateless JWT-based authentication (no sessions)
- BCrypt password hashing
- Role-based access control via Spring Security
- CORS configured via `app.cors.allowed-origins` property

---

## Building for Production

```bash
mvn clean package -DskipTests
java -jar target/fundtracker-backend-1.0.0.jar
```

Set environment variables for production:

```bash
export SPRING_DATA_MONGODB_URI=mongodb+srv://user:pass@cluster.mongodb.net/fundtracker
export APP_JWT_SECRET=your-very-long-secret-key
export APP_CORS_ALLOWED_ORIGINS=https://your-frontend-domain.com
```

---

## Running Tests

```bash
mvn test
```

---

## Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/your-feature`)
3. Commit your changes (`git commit -m 'Add some feature'`)
4. Push to the branch (`git push origin feature/your-feature`)
5. Open a Pull Request

---

## License

This project is licensed under the MIT License.
